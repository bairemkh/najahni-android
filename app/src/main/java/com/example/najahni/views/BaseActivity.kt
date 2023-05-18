package com.example.najahni.views

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.example.najahni.utils.SharedPrefsNajahni
import java.util.*


open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(updateBaseContextLocale(newBase))
    }
    private fun updateBaseContextLocale(context: Context): Context {
        val language = SharedPrefsNajahni.getSavedLanguageCode(context)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val configuration = Configuration(res.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }
    fun updateLanguage() {
        recreate()
    }
}