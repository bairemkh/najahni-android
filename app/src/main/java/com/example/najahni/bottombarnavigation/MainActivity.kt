package com.example.najahni.bottombarnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.najahni.R
import com.example.najahni.views.cart.CartFragment
import com.example.najahni.views.courses.CoursesFragment
import com.example.najahni.views.favorit.FavoriteFragment
import com.example.najahni.views.favorit.FavoritFragment
import com.example.najahni.views.home.HomeFragment
import com.example.najahni.views.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hosting_bottom_bar_nav)
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
                        loadFragment(ProfileFragment())
                        true
                    }
                    else -> {
                        true
                    }
                }

        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fram_container,fragment)
        transaction.commit()
    }

}