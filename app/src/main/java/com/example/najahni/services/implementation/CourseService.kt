package com.example.najahni.services.implementation

import android.util.Log
import com.example.najahni.models.*
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Level
import com.example.najahni.services.retrofitInterfaces.ICourseRetrofit
import com.example.najahni.utils.Consts
import com.example.najahni.utils.IService
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CourseService : IService<Course> {
    val api: ICourseRetrofit = Consts.retrofit.create(ICourseRetrofit::class.java)
    override fun create(o: Course) {
        TODO("Not yet implemented")
    }

    override fun retrieve(id: String): Course {
        TODO("Not yet implemented")
    }

    override fun update(id: String) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }


    fun getAllCourses(returningResponse : (Int,List<Course>?) -> Unit) {
        val response = CourseService.api.getAllCourses().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val body = response.body()?.string().orEmpty()
                    val gson = Gson()
                    val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject
                    val courses = jsonObject["courses"].asJsonArray.map { o -> makeCourseFromJson(o.asJsonObject) }
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
    fun makeCourseFromJson(jsonObject: JsonObject): Course {
        return Course(jsonObject.get("_id").asString,
            jsonObject.get("title").asString,
            jsonObject.get("description").asString,
            jsonObject.get("fields").asJsonArray.map { f -> Field.valueOf(f.asString) },
            Level.valueOf(jsonObject.get("level").asString),
            jsonObject.get("image").asString,
            jsonObject.get("isPaid").asBoolean,
            jsonObject.get("price").asDouble,
            UserService.makeUserFromJson(jsonObject.get("idowner") as JsonObject),
            jsonObject.get("sections").asJsonArray.map { obj-> Section.jsonToSection(obj.asJsonObject)},
            jsonObject.get("isArchived").asBoolean,
            jsonObject.get("lesson_number").asInt,
            jsonObject.get("quiz").asJsonArray.map { t-> Quiz.jsonToQuiz(t.asJsonObject) }
        )
    }
}