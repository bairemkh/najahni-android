package com.example.najahni.services.implementation

import android.util.Log
import com.example.najahni.models.Course
import com.example.najahni.models.Lesson
import com.example.najahni.models.Quiz
import com.example.najahni.models.Section
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Level
import com.example.najahni.services.retrofitInterfaces.ICourseRetrofit
import com.example.najahni.utils.ApiResponseHandling
import com.example.najahni.utils.Consts
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream


object CourseService  {
    private val api: ICourseRetrofit = Consts.retrofit.create(ICourseRetrofit::class.java)
     fun create(token:String,course: Course,file: File,result: ApiResponseHandling) {
         val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
         val imagePart = MultipartBody.Part.createFormData("image", file.name, requestBody)
         val title = course.title.toRequestBody("text/plain".toMediaTypeOrNull())
         val description = course.description.toRequestBody("text/plain".toMediaTypeOrNull())
         val level = course.level.name.toRequestBody("text/plain".toMediaTypeOrNull())
         val isPaid = course.isPaid.toString().toRequestBody("text/plain".toMediaTypeOrNull())
         val price = course.price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
         api.addCourse(token,title,imagePart,description, level, isPaid, price).enqueue(object:Callback<ResponseBody>{
             override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                 Log.e("adding response",response.message())
                 if(response.isSuccessful) {
                     val body = response.body()?.string().orEmpty()
                     val gson = Gson()
                     val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                     val jsonObject = jsonElement.asJsonObject
                     Log.e("user verif: ", jsonObject.toString())
                     result.onSuccess(makeCourseFromJson(jsonObject))
                 }else{
                     result.onError(response.code(),response.message())
                 }
             }
             override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                 Log.e("error while adding",t.message!!)
                 result.onFailure(t.message!!)
             }
         })
    }

     fun retrieve(id: String): Course {
        TODO("Not yet implemented")
    }

     fun update(token:String,course:Course,result:(Boolean)->Unit) {
        api.updateCourse(token,course.id!!,course).enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("response","${response.code()} : ${response.message()}")
                Log.e("course","$course")
                if (response.isSuccessful)
                    result(true)
                else
                    result(false)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                result(false)
            }

        })
    }

     fun delete(id: String) {
        TODO("Not yet implemented")
    }


    fun getAllCourses(returningResponse : (Int,List<Course>?) -> Unit) {
        api.getAllCourses().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val body = response.body()?.string().orEmpty()
                    val gson = Gson()
                    val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject
                    Log.e("error",jsonObject.toString())
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

    fun saveLessonVideo(file: File, title:String, result: (Lesson?,Boolean) -> Unit) {
        val videoRequest = file.asRequestBody("video/*".toMediaTypeOrNull())
        val videoPart = MultipartBody.Part.createFormData("video", file.name, videoRequest)
        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
        api.saveLessonVideo(titlePart,videoPart).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val body = response.body()?.string().orEmpty()
                    val gson = Gson()
                    val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject
                    result(
                        Lesson(
                            UUID.randomUUID().toString()
                            ,jsonObject.get("title").asString
                            ,jsonObject.get("video").asString
                            ,jsonObject.get("duration").asInt
                    ),true)
                }
                else{
                    Log.e("changing video error",response.message())
                    result(null,false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                result(null,false)
            }

        })
    }
    fun makeCourseFromJson(jsonObject: JsonObject): Course {
        return Course(jsonObject.get("_id").asString,
            jsonObject.get("title").asString,
            jsonObject.get("description").asString,
            jsonObject.get("fields").asJsonArray.map { f -> Field.valueOf(f.asString) }.toMutableList(),
            jsonObject.get("rating").asInt,
            Level.valueOf(jsonObject.get("level").asString),
            jsonObject.get("image").asString,
            jsonObject.get("isPaid").asBoolean,
            jsonObject.get("price").asDouble,
            UserService.makeUserFromJson(jsonObject.get("idowner") as JsonObject),
            jsonObject.get("sections").asJsonArray.map { obj-> Section.jsonToSection(obj.asJsonObject)}.toMutableList(),
            jsonObject.get("isArchived").asBoolean,
            jsonObject.get("lesson_number").asInt,
            jsonObject.get("quiz").asJsonArray.map { t-> Quiz.jsonToQuiz(t.asJsonObject) }.toMutableList()
        )
    }
}