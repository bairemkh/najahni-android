package com.example.najahni.views.editProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling
import java.io.File

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

    fun changeImage(file: File,token:String,imageIsChanged:(Boolean)->Unit){
        UserService.changeImage(token,file,object:ApiResponseHandling{
            override fun onSuccess(data: Any) {
                message.value = "Ok"
                editSuccess.value = true
                imageIsChanged(true)
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                message.value = "Error while changing the profile picture"
                editSuccess.value = false
                imageIsChanged(false)
            }

            override fun onFailure(errorMessage: String) {
                message.value = "Connectivity error"
                editSuccess.value = false
                imageIsChanged(false)
            }

        })
    }
}