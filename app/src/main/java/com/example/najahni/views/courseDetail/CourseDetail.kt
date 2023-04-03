package com.example.najahni.views.courseDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.najahni.R
import com.example.najahni.models.Course
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.Favorits
import com.example.najahni.roomDB.AppDatabase
import com.example.najahni.utils.Consts
import com.example.najahni.utils.Consts.SELECTED_COURSE_INTENT
import com.example.najahni.views.profileTrainer.TabAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class CourseDetail : AppCompatActivity() {
    private lateinit var tablayout : TabLayout
    private lateinit var viewPager : ViewPager
    private lateinit var appDb : AppDatabase
    private lateinit var course : Favorits
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)
        appDb = AppDatabase.getDatabase(this)
        val selectedCourse = intent.getSerializableExtra(SELECTED_COURSE_INTENT) as Course
        tablayout = findViewById(R.id.tablayoutdetail)
        val favbtn = findViewById<FloatingActionButton>(R.id.addCourseToFavBtn)
       /* if (isFavorite(selectedCourse.id!!)){
            favbtn.setBackgroundColor(0xFF0000)
        }*/
        isFavorite(selectedCourse.id!!)

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

        favbtn.setOnClickListener {
            val favorits = Favorits(
                null,selectedCourse.id!!,selectedCourse.title,selectedCourse.image
            )
            addFavorite(favorits)
        }
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
    private fun addFavorite(favorits: Favorits){
        GlobalScope.launch(Dispatchers.IO){
            var favoritsf = appDb.favoritDao().findById(favorits._id)
            if(favoritsf?._id.isNullOrBlank()){
                appDb.favoritDao().insert(favorits)
            }
            else if(favorits._id != favoritsf._id!!){
                appDb.favoritDao().insert(favorits)
            }

        }
        Toast.makeText(this,"Add with success",Toast.LENGTH_SHORT).show()
    }

    private fun isFavorite(id:String){
        GlobalScope.launch {
           course = appDb.favoritDao().findById(id)
        }
    }
}

