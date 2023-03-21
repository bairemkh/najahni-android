package com.example.najahni.views.forgetpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling

class ForgetPasswordViewModel : ViewModel() {
    var message = MutableLiveData<String>()
    var userid = MutableLiveData<String>()
    var emailSend = MutableLiveData<Boolean>()

    fun OnSendClicked(email: String) {
        UserService.forgetPassword(email,object : ApiResponseHandling{
            override fun onSuccess(data: Any) {
               userid.value = data as String
               message.value =  "E-mail send to " + email
               emailSend.value =  true
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                message.value = when (errorCode) {
                    404 -> "User not found"
                    401 -> "User not verified"
                    else -> "Error"

                }
                emailSend.value =  false
            }

            override fun onFailure(errorMessage: String) {
                message.value = "Server Error"
                emailSend.value =  false
            }

        })
    }
}