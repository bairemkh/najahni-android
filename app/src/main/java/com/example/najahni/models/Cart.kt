package com.example.najahni.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_course")

data class Cart(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "image")val image : String,
    @ColumnInfo(name = "price")val price : Double,
)
