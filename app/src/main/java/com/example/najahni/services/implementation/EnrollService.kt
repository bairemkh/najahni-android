package com.example.najahni.services.implementation

import android.util.Log
import com.example.najahni.models.Course
import com.example.najahni.models.Enroll
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Level
import com.example.najahni.services.retrofitInterfaces.ICourseRetrofit
import com.example.najahni.services.retrofitInterfaces.IEnrollRetrofit
import com.example.najahni.utils.Consts
import com.example.najahni.utils.IService
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object EnrollService : IService<Enroll> {
    val api: IEnrollRetrofit = Consts.retrofit.create(IEnrollRetrofit::class.java)
    override fun create(o: Enroll) {
        TODO("Not yet implemented")
    }

    override fun retrieve(id: String): Enroll {
        TODO("Not yet implemented")
    }

    override fun update(id: String) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    fun getAllCourses(token: String,returningResponse : (Int,List<Enroll>?) -> Unit) {
        val response = EnrollService.api.getMyCourses(token).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val body = response.body()?.string().orEmpty()
                    val gson = Gson()
                    val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject
                    val courses = jsonObject["enrolled"].asJsonArray.map { o ->
                        EnrollService.makeCourseFromJson(
                            o.asJsonObject
                        )
                    }
                    // Log.e("courses ========", jsonObject.toString())
                    returningResponse(200, courses)
                } else {
                    Log.e("response 302"," ====> ${response.body()?.string().orEmpty()}")
                    returningResponse(302, null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                returningResponse(500, null)
            }
        })
    }

    fun makeCourseFromJson(jsonObject: JsonObject): Enroll {
        return Enroll(jsonObject.get("_id").asString,
            jsonObject.get("userid").asString,
            CourseService.makeCourseFromJson(jsonObject["courseid"] as JsonObject),
            jsonObject.get("progress").asFloat,
        )
    }

}