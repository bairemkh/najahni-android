package com.example.najahni.views.editProfile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.models.CurrentUser
import com.example.najahni.utils.Consts
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.views.changepassword.ChangePasswordActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class EditProfileActivity : AppCompatActivity() {
    lateinit var viewModel : EditProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        viewModel = ViewModelProvider(this)[com.example.najahni.views.editProfile.EditProfileViewModel::class.java]
        findViewById<FloatingActionButton>(R.id.changeImageBtn).setOnClickListener {
            val requestPermissionLauncher =
                registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent, 200)
                    } else {
                        Toast.makeText(this,"You can't change the image unless you give access",Toast.LENGTH_LONG).show()
                    }
                }

        }
        findViewById<EditText>(R.id.editfirstname).setText(CurrentUser.firstname)
        findViewById<EditText>(R.id.editlastname).setText(CurrentUser.lastname)
        findViewById<EditText>(R.id.editemail).setText(CurrentUser.email)
        viewModel= ViewModelProvider(this)[EditProfileViewModel::class.java]
        findViewById<TextView>(R.id.changepwd).setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
        Picasso.get().load(Consts.BASE_URL1 + CurrentUser.image).into(findViewById<CircleImageView>(R.id.editProfileImage));
        findViewById<Button>(R.id.editbtn).setOnClickListener {
            val sharedPreferences: SharedPreferences = getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)
            val token = SharedPrefsNajahni.getToken(sharedPreferences)
            viewModel.editProfile(
                token,
                findViewById<EditText>(R.id.editfirstname).text.toString(),
                findViewById<EditText>(R.id.editlastname).text.toString(),
                findViewById<EditText>(R.id.editemail).text.toString(),
            )
        }

        viewModel.editSuccess.observe(this){
            if(it){
                CurrentUser.firstname = findViewById<EditText>(R.id.editfirstname).text.toString()
                CurrentUser.lastname = findViewById<EditText>(R.id.editlastname).text.toString()
                CurrentUser.email = findViewById<EditText>(R.id.editemail).text.toString()
                Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK) {
            val selectedImageUri = data?.data ?: return
            val file= File(getRealPathFromURI(selectedImageUri))
            val sharedPreferences: SharedPreferences = getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)
            val token = SharedPrefsNajahni.getToken(sharedPreferences)
            viewModel.changeImage( file,token){ canChange->
                if(canChange){
                    findViewById<CircleImageView>(R.id.editProfileImage).setImageURI(selectedImageUri)
                }else{
                    Toast.makeText(this,viewModel.message.value,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return "Error"
    }
}