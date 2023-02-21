package com.example.najahni.views.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.User
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.utils.SharedPrefsNajahni.SHARED_PREFS


class LoginView : AppCompatActivity() {
    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            viewModel.onLoginClicked(
                findViewById<EditText>(R.id.loginEmail).text.toString(),
                findViewById<EditText>(R.id.loginPassword).text.toString()
            )
        }
        viewModel.loginSuccess.observe(this, Observer {
            if (!it) {
                    Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
            }
            else{
                Log.e("token",viewModel.token.value.orEmpty())
               val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        })
    }
}