package com.example.najahni.views.resetpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling

class ResetPasswordViewModel : ViewModel(){
    var loginSuccess = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var tokenSaved = MutableLiveData<String>()

    fun resetPasswordClicked(token: String , password: String){
        UserService.newPassword(token,password,object : ApiResponseHandling{
            override fun onSuccess(data: Any) {
                tokenSaved.value=data as String
                message.value="success"
                loginSuccess.value = true            }

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