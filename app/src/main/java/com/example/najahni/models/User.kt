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
                var courses:List<String>?)
object CurrentUser{
    var isConnected:Boolean = false
    private var _id:String? = null
    private var firstname:String? = null
    private var lastname:String? = null
    private var email: String? = null
    private var password: String? = null
    private var role: Role? = null
    private var fields: List<Field>? = null
    private var image: String? = null
    private var isVerified: Boolean? = null
    var otp: String? = null
    private var courses:List<String>? = null
    fun setCurrentUser(_id:String?,firstname:String,lastname:String,email: String,password: String,
                       role: Role,fields: List<Field>,image: String,isVerified: Boolean,otp: String,
                       courses:List<String>?){
        this.isConnected = true
        this._id=_id
        this.email=email
        this.firstname=firstname
        this.lastname=lastname
        this.password=password
        this.role=role
        this.fields=fields
        this.image=image
        this.isVerified=isVerified
        this.otp=otp
        this.courses=courses
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