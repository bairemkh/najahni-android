package com.example.najahni.models

import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Role

data class User(var _id:String?, var firstname:String, var lastname:String, var email: String,
                var password: String,
                var role: Role,
                var fields: List<Field>,
                var image: String,
                var isVerified: Boolean,
                var otp: String,
                var courses:List<String>?):java.io.Serializable
object CurrentUser{
    var isConnected:Boolean = false
     var _id:String? = null
     var firstname:String? = null
     var lastname:String? = null
     var email: String? = null
     var password: String? = null
     var role: Role? = null
     var fields: List<Field>? = null
     var image: String? = null
     var isVerified: Boolean? = null
    var otp: String? = null
    var courses:List<String>? = null
    fun setCurrentUser(user:User){
        this.isConnected = true
        this._id=user._id
        this.email=user.email
        this.firstname=user.firstname
        this.lastname=user.lastname
        this.password=user.password
        this.role=user.role
        this.fields=user.fields
        this.image=user.image
        this.isVerified=user.isVerified
        this.otp=user.otp
        this.courses=user.courses
    }
    fun getCurrentUser():User?{
        return if (isConnected)
            User(_id, firstname!!, lastname!!, email!!, password!!, role!!, fields!!, image!!,
                isVerified!!, otp!!, courses!!)
        else
            null
    }
    fun disconnect(){
        isConnected=false
        this._id=null
        this.email=null
        this.firstname=null
        this.lastname=null
        this.password=null
        this.role=null
        this.fields=null
        this.image=null
        this.otp= null
        this.isVerified=null
        this.courses=null
    }
}