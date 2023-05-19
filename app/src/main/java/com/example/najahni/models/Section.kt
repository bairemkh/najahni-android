package com.example.najahni.models

import com.google.gson.JsonObject

data class Section(val id:String?=null,var title:String,var lessons:MutableList<Lesson>):java.io.Serializable{
    companion object{
        fun jsonToSection(jsonObject: JsonObject):Section{
        return Section(
            jsonObject.get("_id").asString
            ,jsonObject.get("title").asString
            ,jsonObject.get("lessons").asJsonArray.map { obj-> Lesson.jsonToSection(obj.asJsonObject)}.toMutableList()
        )
        }
    }
}
