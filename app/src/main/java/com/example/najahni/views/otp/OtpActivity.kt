package com.example.najahni.views.otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.views.login.LoginViewModel

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
                print(viewModel.message)
            }else{
                print(viewModel.message)
            }
        }

    }
}