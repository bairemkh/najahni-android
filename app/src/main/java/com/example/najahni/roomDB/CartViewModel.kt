package com.example.najahni.roomDB

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.models.Cart
import com.example.najahni.models.Favorits

class CartViewModel : ViewModel() {
    var cartList: LiveData<List<Cart>>? = null

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
}