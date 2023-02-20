package com.example.najahni.services.retrofitInterfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IUserRetrofit {
    @POST("user/signin")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    @GET("user/profile")
    fun getProfile(@Header("Authorization") authorization: String): Call<ResponseBody>
}