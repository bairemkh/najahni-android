package com.example.najahni.views.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling

class ChangePasswordViewModel : ViewModel() {
    var changeSuccess = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var tokenSaved = MutableLiveData<String>()

    fun changePasswordClick(token: String,password:String,newPassword:String){
        UserService.changePassword(token,password,newPassword,object : ApiResponseHandling{
            override fun onSuccess(data: Any) {
                message.value = data as String
                changeSuccess.value = true
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                message.value = when (errorCode) {
                    404 -> "User not found"
                    403 -> "incorrect password"
                    else -> "Error"
                }
                changeSuccess.value = false
            }

            override fun onFailure(errorMessage: String) {
                message.value = "Server Error"
                changeSuccess.value = false
            }

        })
    }
}