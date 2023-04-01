package com.example.najahni.services.retrofitInterfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface IEnrollRetrofit {
    @GET("enroll/getmycourses")
    fun getMyCourses(@Header("Authorization") authorization: String): Call<ResponseBody>
}