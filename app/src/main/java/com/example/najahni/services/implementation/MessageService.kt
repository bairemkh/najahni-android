package com.example.najahni.services.implementation

import android.util.Log
import com.example.najahni.models.*
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Level
import com.example.najahni.services.retrofitInterfaces.IEnrollRetrofit
import com.example.najahni.services.retrofitInterfaces.IMessageRetrofit
import com.example.najahni.utils.Consts
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MessageService {
    val api: IMessageRetrofit = Consts.retrofit.create(IMessageRetrofit::class.java)

    fun getAllContact(token: String,returningResponse : (Int,List<UserWithMessage>?) -> Unit) {
        val response = MessageService.api.getContacts(token).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val body = response.body()?.string().orEmpty()
                    val gson = Gson()
                    val jsonElement: JsonElement = gson.fromJson(body, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject
                    var contacts : MutableList<UserWithMessage> = mutableListOf()
                    //var messages : List<Message>?
                    jsonObject["Contacts"].asJsonArray.forEach { o ->
                       val user = UserService.makeUserFromJson(
                            o.asJsonObject["User"].asJsonObject
                        )
                        val messages = o.asJsonObject["Messages"].asJsonArray.map { m ->
                            makeCourseFromJson(m.asJsonObject)
                        }
                        val userWithMessage = UserWithMessage(user,messages)
                        Log.e("Contact ========", userWithMessage.toString())
                        contacts.add(userWithMessage)


                    }
                    Log.e("Contact ========", contacts.toString())
                    returningResponse(200, contacts)
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

    fun makeCourseFromJson(jsonObject: JsonObject): Message {
        return Message(jsonObject.get("_id").asString,
            jsonObject.get("msgContent").asString,
            jsonObject.get("senderid").asString,
            jsonObject.get("receiverid").asString,
        )
    }
}