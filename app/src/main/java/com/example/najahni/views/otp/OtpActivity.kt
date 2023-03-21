package com.example.najahni.views.otp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.views.login.LoginViewModel
import com.example.najahni.views.resetpassword.ResetPasswordActivity

class OtpActivity : AppCompatActivity() {
    private lateinit var viewModel: OtpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        viewModel = ViewModelProvider(this)[OtpViewModel::class.java]
        findViewById<Button>(R.id.sendotpbtn).setOnClickListener {
            val otp = findViewById<EditText>(R.id.box1).text.toString() + findViewById<EditText>(R.id.box2).text.toString() + findViewById<EditText>(R.id.box3).text.toString() + findViewById<EditText>(R.id.box4).text.toString()
            val id = intent.getStringExtra("userid")
            viewModel.sendOtpClick(id.toString(),otp.toString())
            Log.e("otp=======", otp)
            Log.e("id=======", id.toString())
        }
        viewModel.success.observe(this){
            if(it){
                val sharedPreferences: SharedPreferences = getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)
                SharedPrefsNajahni.setToken(sharedPreferences, viewModel.token.value.orEmpty())
                val intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
            }
        }

    }
}