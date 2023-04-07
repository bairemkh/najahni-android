package com.example.najahni.views.courseDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.najahni.R
import com.example.najahni.models.Cart
import com.example.najahni.models.Course
import com.example.najahni.models.Favorits
import com.example.najahni.roomDB.AppDatabase
import com.example.najahni.utils.Consts
import com.example.najahni.utils.Consts.SELECTED_COURSE_INTENT
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        val enrollbtn = findViewById<ExtendedFloatingActionButton>(R.id.enroll_btn)

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

        if(selectedCourse.isPaid){
            enrollbtn.setText("Add to cart")
            enrollbtn.setOnClickListener {
                val carts = Cart(
                    null,selectedCourse.id!!,selectedCourse.title,selectedCourse.image,selectedCourse.price
                )
                addCart(carts)
            }
        }
        viewPager = findViewById(R.id.viewLoaderdetail)
        tablayout.addTab(tablayout.newTab().setText("Lessons"))
        tablayout.addTab(tablayout.newTab().setText("Reviews"))
        tablayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabAdapterDetail(this,supportFragmentManager,tablayout.tabCount,selectedCourse)
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
            } else {
                appDb.favoritDao().delete(favoritsf)
            }

        }
        Toast.makeText(this,"Add with success",Toast.LENGTH_SHORT).show()
    }

    private fun addCart(cart: Cart){
        GlobalScope.launch(Dispatchers.IO){
            var cartf = appDb.cartDao().findById(cart._id)
            if(cartf?._id.isNullOrBlank()){
                appDb.cartDao().insert(cart)
            }
            else if(cart._id != cartf._id!!){
                appDb.cartDao().insert(cart)
            }

        }
    }
}

