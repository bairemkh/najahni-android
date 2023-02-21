package com.example.najahni.bottombarnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.najahni.R
import com.example.najahni.views.cart.CartFragment
import com.example.najahni.views.courses.CoursesFragment
import com.example.najahni.views.favorit.FavoritFragment
import com.example.najahni.views.home.HomeFragment
import com.example.najahni.views.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hosting_bottom_bar_nav)

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> setCurrentFragment(HomeFragment())
                    R.id.mycourses -> setCurrentFragment(CoursesFragment())
                    R.id.cart -> setCurrentFragment(CartFragment())
                    R.id.favorite -> setCurrentFragment(FavoritFragment())
                    R.id.profile -> setCurrentFragment(ProfileFragment())
                }
            true
        }
    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fram_container,fragment)
            commit()
        }
}