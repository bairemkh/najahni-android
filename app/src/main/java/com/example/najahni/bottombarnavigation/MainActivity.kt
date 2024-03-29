package com.example.najahni.bottombarnavigation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.najahni.R
import com.example.najahni.models.CurrentUser
import com.example.najahni.roomDB.AppDatabase
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.utils.SocketHandler
import com.example.najahni.views.cart.CartFragment
import com.example.najahni.views.courses.CoursesFragment
import com.example.najahni.views.favorit.FavoriteFragment
import com.example.najahni.views.home.HomeFragment
import com.example.najahni.views.profile.ProfileFragment
import com.example.najahni.views.profileTrainer.ProfileTrainerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var appDatabase: AppDatabase
    private lateinit var languageObserver: Observer<String>
    private var shouldRestart = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hosting_bottom_bar_nav)
        if(CurrentUser.getCurrentUser()==null){
            Log.e("error","Null user")
        }
        appDatabase = AppDatabase.getDatabase(this)
        val intent = Intent(this, SocketHandler::class.java)

        startService(intent)
        //SocketHandler.setSocket()
        //ocketHandler.establishConnection()
        loadFragment(HomeFragment())
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        loadFragment(HomeFragment())
                        true
                    }
                    R.id.mycourses -> {
                        loadFragment(CoursesFragment())
                        true
                    }
                    R.id.cart -> {
                        loadFragment(CartFragment())
                        true
                    }
                    R.id.favorite -> {
                        loadFragment(FavoriteFragment())
                        true
                    }
                    R.id.profile -> {
                        if(CurrentUser.role?.name.equals("Student")){
                            loadFragment(ProfileFragment())

                        }else{
                            loadFragment(ProfileTrainerFragment())
                        }
                        true
                    }
                    else -> {
                        true
                    }
                }

        }
    }
    override fun onResume() {
        super.onResume()
        Log.e("resumed","main resumed")
        languageObserver= Observer {
            Log.e("changed","to $it")
            shouldRestart=true
        }
        if(shouldRestart){
            shouldRestart=false
            recreate()
        }
        SharedPrefsNajahni.languageLiveData.observe(this,languageObserver)
    }
    override fun onPause() {
        super.onPause()
        Log.e("paused","main paused")
        SharedPrefsNajahni.languageLiveData.removeObserver(languageObserver)
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fram_container,fragment)
        transaction.commit()
    }
}