package com.example.najahni.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_course")
data class Favorits(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "image")val image : String)
