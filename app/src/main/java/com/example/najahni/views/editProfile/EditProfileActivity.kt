package com.example.najahni.views.editProfile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.models.CurrentUser
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.views.changepassword.ChangePasswordActivity

class EditProfileActivity : AppCompatActivity() {
    lateinit var viewModel : EditProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        findViewById<EditText>(R.id.editfirstname).setText(CurrentUser.firstname)
        findViewById<EditText>(R.id.editlastname).setText(CurrentUser.lastname)
        findViewById<EditText>(R.id.editemail).setText(CurrentUser.email)
        findViewById<TextView>(R.id.changepwd).setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

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
}