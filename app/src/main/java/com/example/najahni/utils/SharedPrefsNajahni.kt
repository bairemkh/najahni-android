package com.example.najahni.utils

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


object SharedPrefsNajahni {
    const val SHARED_PREFS="MySharedPref"
    private const val USER_TOKEN="UserToken"
    /**
     * Init in the concerned activity
     * val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
     * SharedPrefsNajahni.setToken(sharedPreferences, viewModel.token.value.orEmpty())
    */
    fun setToken(sharedPreferences:SharedPreferences,token:String){
        val editor = sharedPreferences.edit()
        editor.putString(USER_TOKEN, token).apply()
    }
    fun getToken(sharedPreferences:SharedPreferences):String{
        return sharedPreferences.getString(USER_TOKEN,"")!!
    }
}