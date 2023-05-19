package com.example.najahni.roomDB

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.models.Cart
import com.example.najahni.models.Favorits
import com.example.najahni.services.implementation.EnrollService
import com.example.najahni.utils.ApiResponseHandling

class CartViewModel : ViewModel() {
    var cartList: LiveData<List<Cart>>? = null
    var payementSucess = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    fun insertCart(context: Context,cart: Cart){
        CartRepository.addToCart(context,cart)
    }

    fun cartList(context: Context):LiveData<List<Cart>>? {
        cartList = CartRepository.cartList(context)
        return cartList
    }

    fun deleteCart(context: Context,cart: Cart){
        CartRepository.deleteFromCart(context,cart)
    }

    fun deleteById(context: Context,id:String){
        CartRepository.getOne(context,id)
    }

    fun payement(amount: String){
        EnrollService.initPayement(amount,object : ApiResponseHandling {
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