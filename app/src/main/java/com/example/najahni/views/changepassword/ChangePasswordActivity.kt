package com.example.najahni.views.changepassword

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

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var viewModel: ChangePasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        viewModel = ViewModelProvider(this)[ChangePasswordViewModel::class.java]

        findViewById<Button>(R.id.changepwdbtn).setOnClickListener {
            val oldPwd = findViewById<EditText>(R.id.oldpassword).text.toString()
            val pwd = findViewById<EditText>(R.id.editnewpassword).text.toString()
            val confirmPwd = findViewById<EditText>(R.id.editconfirmpassword).text.toString()

            if(pwd.equals(confirmPwd)){
                val sharedPreferences: SharedPreferences = getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)
                val token = SharedPrefsNajahni.getToken(sharedPreferences)
                viewModel.changePasswordClick(token,oldPwd,pwd)
            }

        }

        viewModel.changeSuccess.observe(this){
            if(it){
                Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
            }
        }
    }
}