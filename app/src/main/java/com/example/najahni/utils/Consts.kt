package com.example.najahni.utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Consts {

    const val USER_NAME_INTENT="userName"
    const val USER_LASTNAME_INTENT="userLastName"
    const val USER_EMAIL_INTENT="userEmail"
    const val USER_PASSWORD_INTENT="userPassword"
    const val SELECTED_COURSE_INTENT="selectedCourse"


    //val BASE_URL1 = "http://192.168.1.52:9090"
    val BASE_URL1 = "http://10.0.2.2:9090"
    //val BASE_URL="http://192.168.1.52:9090/"
    val BASE_URL="http://10.0.2.2:9090/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
}