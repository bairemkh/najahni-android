package com.example.najahni.models

import com.google.gson.JsonObject

data class Quiz(var id:String,var question:String,var propositions:List<String>,var answerIndex:Int){
    companion object{
        fun jsonToQuiz(jsonObject: JsonObject):Quiz{
            return Quiz(
                jsonObject.get("_id").asString
                ,jsonObject.get("question").asString
                ,jsonObject.get("props").asJsonArray.map { obj-> obj.asString}
                ,jsonObject.get("correctIndex").asInt
            )
        }
    }
}
