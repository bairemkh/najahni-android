package com.example.najahni.views.editProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling

class EditProfileViewModel : ViewModel() {
    var editSuccess = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var tokenSaved = MutableLiveData<String>()

    fun editProfile(token: String, firstname: String, lastname: String, email: String) {
        UserService.editProfile(token, firstname, lastname, email, object : ApiResponseHandling {
            override fun onSuccess(data: Any) {
                tokenSaved.value = data as String
                message.value = "success"
                editSuccess.value = true
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                message.value = when (errorCode) {
                    404 -> "User not found"
                    403 -> "incorrect password"
                    else -> "Error"
                }
                editSuccess.value = false
            }

            override fun onFailure(errorMessage: String) {
                message.value = "Server Error"
                editSuccess.value = false
            }

        })
    }
}