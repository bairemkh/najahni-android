package com.example.najahni.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.lifecycle.MutableLiveData
import java.util.*


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
    const val LANGUAGE_PREFS_KEY = "language_prefs"
    private const val LANGUAGE_CODE_KEY = "language_code"
    val languageLiveData: MutableLiveData<String> = MutableLiveData()


    fun setLocale(context: Context, languageCode: String) {
        saveLanguageCode(context, languageCode)
        languageLiveData.value=languageCode
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        configuration.setLocale(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun getSavedLanguageCode(context: Context): String {
        val prefs = getPreferences(context)
        return prefs.getString(LANGUAGE_CODE_KEY, "") ?: ""
    }
    fun initializeAppLanguage(context: Context) {
        val savedLanguageCode = getSavedLanguageCode(context)
        setLocale(context, savedLanguageCode)
    }
    fun saveLanguageCode(context: Context, languageCode: String) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(LANGUAGE_CODE_KEY, languageCode)
        editor.apply()
    }

    fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(LANGUAGE_PREFS_KEY, Context.MODE_PRIVATE)
    }
}