package com.example.najahni.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.najahni.models.Cart

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_course")
    fun getAll():LiveData<List<Cart>>

    @Query("SELECT * FROM cart_course WHERE _id LIKE :id LIMIT 1")
    suspend fun findById(id : String): Cart

    @Query("SELECT * FROM cart_course WHERE _id LIKE :id LIMIT 1")
    fun inCart(id : String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cart: Cart)

    @Delete
    suspend fun delete(cart: Cart)
}