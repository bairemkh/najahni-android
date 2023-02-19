package com.example.najahni.views.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling

class LoginViewModel() : ViewModel() {
    val loginSuccess = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    val token = MutableLiveData<String>()
    fun onLoginClicked(email: String, password: String) {
        UserService.login(email, password, object : ApiResponseHandling {
            override fun onSuccess(data: Any) {
                loginSuccess.value = true
                token.value = data as String
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                loginSuccess.value = false
                message.value = when (errorCode) {
                    404 -> "User not found"
                    403 -> "incorrect password"
                    else -> "Error"
                }
            }

            override fun onFailure(errorMessage: String) {
                loginSuccess.value = false
                message.value = "Server Error"
            }

        })
    }
}