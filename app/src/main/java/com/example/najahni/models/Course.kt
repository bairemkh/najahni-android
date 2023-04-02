package com.example.najahni.models

import android.os.Parcelable
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Level

data class Course(var id :String?, var title:String, var description: String,var fields: List<Field>,
                  var level : Level,var image: String,var isPaid : Boolean,var price : Double ,var idOwner: User ,
                    var isArchived : Boolean , var lesson_number : Int):java.io.Serializable
