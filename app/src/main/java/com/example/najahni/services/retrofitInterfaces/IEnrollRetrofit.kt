package com.example.najahni.services.retrofitInterfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface IEnrollRetrofit {
    @GET("enroll/getmycourses")
    fun getMyCourses(@Header("Authorization") authorization: String): Call<ResponseBody>

    @POST("enroll/enrollincourse/{id}")
    fun enrollInCourse(@Header("Authorization") authorization: String,@Path("id") courseId: String): Call<ResponseBody>

    @POST("course/initpayement")
    @FormUrlEncoded
    fun initPayement(@Field("amount") amount: String): Call<ResponseBody>
    @POST("enroll/progress/{id}")
    @FormUrlEncoded
    fun doProgress(@Header("Authorization") authorization: String, @Path("id") id:String,@Field("lessonid")lessonId:String): Call<ResponseBody>
    @POST("enroll/certif/{id}")
    fun getCertificate(@Header("Authorization") authorization: String, @Path("id") id:String): Call<ResponseBody>

    @POST("review/add-review/{id}")
    @FormUrlEncoded
    fun sendReview(@Header("Authorization") authorization: String,@Path("id") id: String,@Field("ratingComp") ratingComp:Int ,@Field("ratingTrainer") ratingTrainer:Int,@Field("ratingContent") ratingContent:Int):Call<ResponseBody>
}