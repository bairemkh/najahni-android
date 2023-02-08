package com.example.najahni.views.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.models.User

class LoginViewModel: ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginStatus = MutableLiveData<Int>()
    fun onLoginClicked(){
        loginStatus.value = if (email.value == "bairem" && password.value == "bairem") 200 else 404
    }
}