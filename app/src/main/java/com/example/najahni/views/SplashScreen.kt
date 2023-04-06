package com.example.najahni.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.User
import com.example.najahni.roomDB.AppDatabase
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.views.login.LoginView

class SplashScreen : AppCompatActivity() {
    lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        appDatabase = AppDatabase.getDatabase(this)
        findViewById<ProgressBar>(R.id.splashScreenLoading).isVisible = false
        val sharedPreferences: SharedPreferences = getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)
        val token = SharedPrefsNajahni.getToken(sharedPreferences)
        if(token.isEmpty()){
            val handler = Handler()
            handler.postDelayed({
                val intent = Intent(this, LoginView::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }else{
            findViewById<ProgressBar>(R.id.splashScreenLoading).isVisible = true
            UserService.getUserProfile(token){
                i, user ->
                if(i==200){
                    CurrentUser.setCurrentUser(user!!)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, LoginView::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}