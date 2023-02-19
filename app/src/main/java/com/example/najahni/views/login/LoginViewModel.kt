package com.example.najahni.views.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(): ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginStatus = MutableLiveData<Int>()
    fun onLoginClicked(con:Context){
        loginStatus.value = if (email.value == "bairem" && password.value == "bairem") 200 else 404
        val sharedPreferences =con.getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
    }
}