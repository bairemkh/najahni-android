package com.example.najahni.services.implementation

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.example.najahni.models.User
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Role
import com.example.najahni.services.retrofitInterfaces.IUserRetrofit
import com.example.najahni.utils.ApiResponseHandling
import com.example.najahni.utils.Consts.retrofit
import com.example.najahni.utils.IService
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object UserService : IService<User> {
    val api: IUserRetrofit = retrofit.create(IUserRetrofit::class.java)

    override fun create(o: User) {
        TODO("Not yet implemented")
    }

    override fun retrieve(id: String): User {
        TODO("Not yet implemented")
    }

    override fun update(id: String) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    fun login(email: String, password: String, responseHandler: ApiResponseHandling) {
        val response = api.login(email, password).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    val body = response.body()?.string().orEmpty()
                    val gson = Gson()
                    val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject
                    Log.i("response", jsonObject.get("data").asString)
                    responseHandler.onSuccess(jsonObject.get("data").asString)
                } else
                    responseHandler.onError(response.code(), response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", "${t.message}")
                responseHandler.onFailure(t.message.toString())
            }
        })
    }

    fun getUserProfile(token: String, responseHandle: ApiResponseHandling) {
        val response = api.getProfile(token).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val body = response.body()?.string().orEmpty()
                    val gson = Gson()
                    val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject
                    val user = makeUserFromJson(jsonObject.get("data").asJsonObject)
                    Log.e("test", "user = $user")
                    responseHandle.onSuccess(user)
                } else
                    responseHandle.onError(response.code(), response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                responseHandle.onFailure(t.message.toString())
            }
        })
    }

    fun makeUserFromJson(jsonObject: JsonObject): User {
        return User(jsonObject.get("_id").asString,
            jsonObject.get("firstname").asString,
            jsonObject.get("lastname").asString,
            jsonObject.get("email").asString,
            jsonObject.get("password").asString,
            Role.valueOf(jsonObject.get("role").asString),
            jsonObject.get("fields").asJsonArray.map { f -> Field.valueOf(f.asString) },
            jsonObject.get("image").asString,
            jsonObject.get("isVerified").asBoolean,
            jsonObject.get("otp").asString,
            jsonObject.get("courses").asJsonArray.map { c -> c.asString })
    }

}