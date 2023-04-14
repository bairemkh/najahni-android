package com.example.najahni.roomDB

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.najahni.models.Cart
import com.example.najahni.models.Favorits
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartRepository {
    companion object {
        var appDatabase : AppDatabase? = null
        var listCart : LiveData<List<Cart>>? = null
        fun initializeDB(context: Context) : AppDatabase {
            return AppDatabase.getDatabase(context)
        }

        fun addToCart(context: Context, cart: Cart){
            appDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                appDatabase!!.cartDao().insert(cart)
            }
        }

        fun cartList(context: Context) : LiveData<List<Cart>>? {
            appDatabase = initializeDB(context)
            listCart = appDatabase!!.cartDao().getAll()
            return listCart
        }

        fun deleteFromCart(context: Context, cart: Cart){
            appDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch{
                appDatabase!!.cartDao().delete(cart)
            }
        }
    }
}