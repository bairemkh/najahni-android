package com.example.najahni.services.implementation

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.example.najahni.models.CurrentUser
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
                    getUserProfile(jsonObject.get("data").asString){ code,user->
                        if(code == 200){
                            Log.e("user","user is $user")
                            CurrentUser.setCurrentUser(user!!)
                            responseHandler.onSuccess(jsonObject.get("data").asString)
                        }else
                            responseHandler.onError(response.code(), "Auth Problem")
                    }

                } else
                    responseHandler.onError(response.code(), response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", "${t.message}")
                responseHandler.onFailure(t.message.toString())
            }
        })
    }

    fun getUserProfile(token: String, returningResponse: (Int,User?)->Unit) {
        val response = api.getProfile(token).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val body = response.body()?.string().orEmpty()
                    val gson = Gson()
                    val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject
                    val user = makeUserFromJson(jsonObject.get("data").asJsonObject)
                    returningResponse(response.code(),user)
                } else {
                    Log.e("response 302"," ====> ${response.body()?.string().orEmpty()}")
                    returningResponse(response.code(),null)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                returningResponse(500,null)
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