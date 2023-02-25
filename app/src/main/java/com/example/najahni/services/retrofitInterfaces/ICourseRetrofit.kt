package com.example.najahni.services.retrofitInterfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ICourseRetrofit {
    @GET("course/getall")
    fun getAllCourses(): Call<ResponseBody>
}