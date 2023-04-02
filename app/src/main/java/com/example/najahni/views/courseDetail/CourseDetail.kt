package com.example.najahni.views.courseDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.utils.Consts
import com.example.najahni.utils.Consts.SELECTED_COURSE_INTENT
import com.example.najahni.views.profileTrainer.TabAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class CourseDetail : AppCompatActivity() {
    private lateinit var tablayout : TabLayout
    private lateinit var viewPager : ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)
        val selectedCourse = intent.getSerializableExtra(SELECTED_COURSE_INTENT) as Course
        tablayout = findViewById(R.id.tablayoutdetail)
        findViewById<TextView>(R.id.courseNameDetail).text=selectedCourse.title
        Picasso.get().load(Consts.BASE_URL1 + selectedCourse.image).into(findViewById<ImageView>(R.id.courseImageDetail))
        findViewById<ChipGroup>(R.id.courseDetailsTags).apply {
            selectedCourse.fields.forEach { field ->
                val chip=Chip(this@CourseDetail)
                chip.text = field.name
                chip.isCheckable=false
                chip.isCloseIconVisible = false
                chip.isClickable = false
                chip.setTextColor(resources.getColor(R.color.white))
                chip.setBackgroundColor(resources.getColor(R.color.primary_color))
                chip.setChipBackgroundColorResource(R.color.primary_color)
                this.addView(chip)
            }
        }
        findViewById<TextView>(R.id.coursePriceDetail).text=if(selectedCourse.isPaid)"${selectedCourse.price} TND" else "Free"
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