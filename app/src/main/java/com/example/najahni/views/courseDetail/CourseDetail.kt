package com.example.najahni.views.courseDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.najahni.R
import com.example.najahni.views.profileTrainer.TabAdapter
import com.google.android.material.tabs.TabLayout

class CourseDetail : AppCompatActivity() {
    private lateinit var tablayout : TabLayout
    private lateinit var viewPager : ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        tablayout = findViewById(R.id.tablayoutdetail)
        viewPager = findViewById(R.id.viewLoaderdetail)
        tablayout.addTab(tablayout.newTab().setText("Lessons"))
        tablayout.addTab(tablayout.newTab().setText("Reviews"))
        tablayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabAdpterDetail(this,supportFragmentManager,tablayout.tabCount)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }
}