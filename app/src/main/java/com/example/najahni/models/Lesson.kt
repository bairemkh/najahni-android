package com.example.najahni.models

import android.util.Log
import com.google.gson.JsonObject


data class Lesson(val id:String?=null,var title:String,var video:String,var duration: Int):java.io.Serializable{
    companion object{
        fun jsonToSection(jsonObject: JsonObject):Lesson{
            Log.e("json:",jsonObject.toString())
            return Lesson(
                jsonObject.get("_id").asString
                ,jsonObject.get("title").asString
                ,jsonObject.get("video").asString
                ,jsonObject.get("duration").asInt
            )
        }
    }
}