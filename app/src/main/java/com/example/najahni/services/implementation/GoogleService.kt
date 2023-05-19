package com.example.najahni.services.implementation

import android.util.Log
import com.example.najahni.models.CurrentUser
import com.example.najahni.utils.ApiResponseHandling
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GoogleService {
    fun googleSignin(email: String, password: String,firstname:String,lastname:String,image:String,responseHandler: ApiResponseHandling) {
        val response = UserService.api.googleSignIn(email, " ",firstname,lastname,image).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    val body = response.body()?.string().orEmpty()
                    val gson = Gson()
                    val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject
                    UserService.getUserProfile(jsonObject.get("token").asString) { code, user ->
                        if (code == 200) {
                            Log.e("user", "user is $user")
                            CurrentUser.setCurrentUser(user!!)
                            responseHandler.onSuccess(jsonObject.get("token").asString)
                        } else
                            responseHandler.onError(response.code(), "Auth Problem")
                    }

                } else
                    responseHandler.onError(response.code(), response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error google", "${t.message}")
                responseHandler.onFailure(t.message.toString())
            }
        })
    }
}