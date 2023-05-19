package com.example.najahni.views.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.services.implementation.EnrollService
import com.example.najahni.utils.ApiResponseHandling

class CartViewModel : ViewModel() {
    var payementSucess = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()

    fun payement(amount: String){
        EnrollService.initPayement(amount,object :ApiResponseHandling{
            override fun onSuccess(data: Any) {
                message.value = data as String
                payementSucess.value = true
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                message.value = when (errorCode) {
                    403 -> "Bad request "
                    else -> "Error"
                }
            }

            override fun onFailure(errorMessage: String) {
                message.value = "Server Error"
                payementSucess.value = false
            }

        })
    }
}