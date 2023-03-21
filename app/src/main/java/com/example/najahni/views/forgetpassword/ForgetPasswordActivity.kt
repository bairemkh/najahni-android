package com.example.najahni.views.forgetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.views.login.LoginViewModel
import com.example.najahni.views.otp.OtpActivity

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var viewModel : ForgetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        viewModel = ViewModelProvider(this)[ForgetPasswordViewModel::class.java]

        findViewById<Button>(R.id.sendbtn).setOnClickListener {
            viewModel.OnSendClicked(findViewById<EditText>(R.id.pwdemail).text.toString())
        }
        viewModel.emailSend.observe(this) {
            if (!it) {
                Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("userid",viewModel.userid.value.toString())
                startActivity(intent)
            }
        }
    }
}