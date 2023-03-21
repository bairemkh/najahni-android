package com.example.najahni.views.otp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling

class OtpViewModel : ViewModel() {
    var success = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()

    fun sendOtpClick(id: String,otp:String) {
        UserService.resetPassword(id,otp,object : ApiResponseHandling{
            override fun onSuccess(data: Any) {
                success.value = data as Boolean
                message.value = "Success"
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                message.value = when (errorCode) {
                    403 -> "incorrect code"
                    else -> "Error"
                }
                success.value =  false
            }

            override fun onFailure(errorMessage: String) {
                message.value = "Server Error"
                success.value = false            }

        })
    }
}