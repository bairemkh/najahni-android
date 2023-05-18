package com.example.najahni.views.profile

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.najahni.R
import com.example.najahni.models.enums.Field
import com.example.najahni.utils.Consts.ACTION_LANGUAGE_CHANGE
import com.example.najahni.utils.SharedPrefsNajahni

class Settings : AppCompatActivity() {
    private lateinit var alertDialog: AlertDialog

    val list = mapOf<String, String>(Pair("en", "English"), Pair("fr", "French"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        findViewById<LinearLayout>(R.id.changeLanguage).setOnClickListener {
            openChoiceDialog()
        }
    }

    private fun openChoiceDialog() {
        val savedLanguageCode = SharedPrefsNajahni.getSavedLanguageCode(this)
        val savedLanguageIndex = list.keys.toList().indexOf(savedLanguageCode)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select an Option")
        builder.setSingleChoiceItems(list.values.toTypedArray(), savedLanguageIndex) { dialog, which ->
            Log.e("Selected language", "The app is in ${list.values.toTypedArray()[which]}")
            val code = list.keys.toList()[which]
            SharedPrefsNajahni.setLocale(applicationContext, code)
            SharedPrefsNajahni.saveLanguageCode(applicationContext, code)
            SharedPrefsNajahni.initializeAppLanguage(this)
            sendBroadcast(Intent(ACTION_LANGUAGE_CHANGE))
            dialog.dismiss()
            recreate()
        }
        alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::alertDialog.isInitialized && alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }
}