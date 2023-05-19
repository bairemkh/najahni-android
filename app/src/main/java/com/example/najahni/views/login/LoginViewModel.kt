package com.example.najahni.views.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.GoogleService
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling

class LoginViewModel() : ViewModel() {
    var loginSuccess = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var token = MutableLiveData<String>()
    lateinit var email: String
    lateinit var firstname : String
    lateinit var lastname: String
    lateinit var picUrl : String
    fun onLoginClicked(email: String, password: String) {
        UserService.login(email, password, object : ApiResponseHandling {
            override fun onSuccess(data: Any) {
                token.value = data as String
                message.value = "success"
                loginSuccess.value = true
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                message.value = when (errorCode) {
                    404 -> "User not found"
                    403 -> "incorrect password"
                    else -> "Error"
                }
                loginSuccess.value = false
            }

            override fun onFailure(errorMessage: String) {
                message.value = "Server Error"
                loginSuccess.value = false
            }

        })
    }

    fun onGoogleSignin(email: String, password: String,firstname:String,lastname:String,image:String){
        GoogleService.googleSignin(email," ",firstname,lastname,image,object :ApiResponseHandling{
            override fun onSuccess(data: Any) {
                token.value = data as String
                message.value = "success"
                loginSuccess.value = true
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                message.value = when (errorCode) {
                    404 -> "User not found"
                    403 -> "incorrect password"
                    else -> "Error"
                }
                loginSuccess.value = false
            }

            override fun onFailure(errorMessage: String) {
                message.value = "Server Error"
                loginSuccess.value = false
            }

        })
    }
}