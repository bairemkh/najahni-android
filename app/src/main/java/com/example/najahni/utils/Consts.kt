package com.example.najahni.utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Consts {
    val BASE_URL1 = "http://192.168.1.12:9090"
    val BASE_URL="http://192.168.1.12:9090/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
}