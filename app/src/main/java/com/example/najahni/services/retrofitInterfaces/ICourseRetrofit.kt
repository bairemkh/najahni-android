package com.example.najahni.services.retrofitInterfaces

import com.example.najahni.models.Course
import com.example.najahni.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ICourseRetrofit {
    @GET("course/getall")
    fun getAllCourses(): Call<ResponseBody>
    @PUT("course/update-myCourse/{id}")
    fun updateCourse(@Header("Authorization") authorization: String, @Path("id") id:String,@Body course: Course): Call<ResponseBody>
    @Multipart
    @POST("lesson/saveLessonVideo")
    fun saveLessonVideo(@Part("title") title: RequestBody,
                        @Part video: MultipartBody.Part): Call<ResponseBody>
    @Multipart
    @POST("course/add-course")
    fun addCourse(
        @Header("Authorization") authorization: String,
        @Part("title") title: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("level") level: RequestBody,
        @Part("isPaid") isPaid: RequestBody,
        @Part("price") price: RequestBody,
        ): Call<ResponseBody>
}