package com.example.najahni.views.login

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
            if (it) {
                UserService.getUserProfile(viewModel.token.value.orEmpty(),object :ApiResponseHandling{
                    override fun onSuccess(data: Any) {
                        val user = data as User
                        CurrentUser.setCurrentUser(user)
                        val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
                        SharedPrefsNajahni.setToken(sharedPreferences,viewModel.token.value.orEmpty())
                    }

                    override fun onError(errorCode: Int, errorMessage: String) {
                        Toast.makeText(this@LoginView,viewModel.message.value,Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(errorMessage: String) {
                        Toast.makeText(this@LoginView,viewModel.message.value,Toast.LENGTH_LONG).show()
                    }

                })
                Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show()
            } else
                Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
        })
    }
}