package com.example.najahni.views.login

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling


class LoginView : AppCompatActivity() {
    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.email.value = findViewById<EditText>(R.id.loginEmail).text.toString()
        viewModel.password.value = findViewById<EditText>(R.id.loginPassword).text.toString()
        findViewById<Button>(R.id.loginBtn).setOnClickListener{
            UserService.login(findViewById<EditText>(R.id.loginEmail).text.toString()
            ,findViewById<EditText>(R.id.loginPassword).text.toString(),object:ApiResponseHandling{
                    override fun onSuccess(data: Any) {
                        Toast.makeText(this@LoginView,"token ${data as String}",Toast.LENGTH_LONG).show()
                        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                    }

                    override fun onError(errorCode: Int, errorMessage: String) {
                        Toast.makeText(this@LoginView,errorMessage,Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(errorMessage: String) {
                        Toast.makeText(this@LoginView,errorMessage,Toast.LENGTH_LONG).show()
                    }

                })
            Log.e("test","Done")
        }
    }

    /*fun login(view: View) {
        view.isClickable = false
        viewModel.onLoginClicked()
        viewModel.loginStatus.observe(this, Observer { result ->
            when (result) {
                200 -> println("ok")
                else -> println("no")
            }
        })
        view.isClickable = true
    }*/
}