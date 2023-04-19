package com.example.najahni.models

import com.google.gson.JsonObject

data class Quiz(var id:String?=null,var question:String,var propositions:MutableList<String>,var answerIndex:Int):java.io.Serializable{
    companion object{
        fun jsonToQuiz(jsonObject: JsonObject):Quiz{
            return Quiz(
                jsonObject.get("_id").asString
                ,jsonObject.get("question").asString
                ,jsonObject.get("props").asJsonArray.map { obj-> obj.asString}.toMutableList()
                ,jsonObject.get("correctIndex").asInt
            )
        }
    }
}
