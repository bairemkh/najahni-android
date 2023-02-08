package com.example.najahni.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.models.User

class LoginView : AppCompatActivity() {
    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.email.value = findViewById<EditText>(R.id.loginEmail).text.toString()
        viewModel.password.value = findViewById<EditText>(R.id.loginPassword).text.toString()

    }

    fun login(view: View) {
        view.isClickable = false
        viewModel.onLoginClicked()
        viewModel.loginStatus.observe(this, Observer { result ->
        when(result){
            200-> println("ok")
            else-> println("no")
        }
        })
        view.isClickable = true
    }
}