package com.example.najahni.views.resetpassword

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.utils.SharedPrefsNajahni

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var viewModel: ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]

        findViewById<Button>(R.id.sendpwdbtn).setOnClickListener {
            val pwd = findViewById<EditText>(R.id.newpassword).text.toString()
            val confirmPwd = findViewById<EditText>(R.id.confirmpassword).text.toString()

            if(pwd.equals(confirmPwd)){
                val sharedPreferences: SharedPreferences = getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)
                val token = SharedPrefsNajahni.getToken(sharedPreferences)
                viewModel.resetPasswordClicked(token,pwd)
            }
        }

        viewModel.loginSuccess.observe(this){
            if(it){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
            }
        }
    }
}