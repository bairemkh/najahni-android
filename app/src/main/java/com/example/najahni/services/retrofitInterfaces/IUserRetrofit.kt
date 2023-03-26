package com.example.najahni.services.retrofitInterfaces

import com.example.najahni.models.User
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

    @POST("user/signup")
    fun signup(@Body user: User): Call<ResponseBody>

    @POST("user/forget-password")
    @FormUrlEncoded
    fun forgetPassword(
        @Field("email") email: String,
    ): Call<ResponseBody>

    @POST("user/reset-password")
    @FormUrlEncoded
    fun resetPassword(
        @Field("id") id: String,
        @Field("otp") otp: String
    ): Call<ResponseBody>

    @PUT("user/editprofile")
    @FormUrlEncoded
    fun newPassword(
        @Header("Authorization") authorization: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    @PUT("user/editprofile")
    @FormUrlEncoded
    fun editProfile(
        @Header("Authorization") authorization: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("email") email: String,
    ): Call<ResponseBody>

    @PUT("user/change-password")
    @FormUrlEncoded
    fun changepassword(
        @Header("Authorization") authorization: String,
        @Field("password") password: String,
        @Field("newpassword") newPassword: String
    ): Call<ResponseBody>
}