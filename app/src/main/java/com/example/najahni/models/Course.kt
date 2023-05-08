package com.example.najahni.models

import android.os.Parcelable
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Level

data class Course(var id :String?, var title:String, var description: String,var fields: MutableList<Field>, var rating:Int,
                  var level : Level,var image: String,var isPaid : Boolean,var price : Double ,var idOwner: User ,
                  var sections:MutableList<Section>,var isArchived : Boolean , var lesson_number : Int,var quiz:MutableList<Quiz>):java.io.Serializable

