package com.example.najahni.views.addCourse


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.najahni.R


class AddCourseActivity : AppCompatActivity() {
    private var pageIndex:MutableLiveData<Int> = MutableLiveData(0)
    lateinit var prev:TextView
    lateinit var nextPage:TextView
    lateinit var confirm:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        val animationOut: Animation = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_out)
        val animationIn: Animation = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in)
        prev=findViewById<TextView?>(R.id.pervBtnAddCourse).apply {
            this.visibility= View.INVISIBLE
        }
        nextPage=findViewById(R.id.nextBtnAddCourse)
        confirm=findViewById<TextView?>(R.id.confirmBtnAddCourse).apply {
            this.visibility= View.INVISIBLE
        }
        loadFragment(CreateCourseFragment())
        prev.setOnClickListener {
            pageIndex.value=pageIndex.value!!-1
            Log.e("click","prev")
        }
        nextPage.setOnClickListener {
            pageIndex.value=pageIndex.value!!+1
        }
        pageIndex.observe(this, Observer { index->
            when (index){
                2-> {
                    nextPage.startAnimation(animationOut)
                    nextPage.visibility=View.INVISIBLE
                    loadFragment(AddQuiz())
                }
                1-> {
                    nextPage.startAnimation(animationIn)
                    nextPage.visibility=View.VISIBLE
                    confirm.startAnimation(animationIn)
                    confirm.visibility=View.VISIBLE
                    prev.startAnimation(animationIn)
                    prev.visibility= View.VISIBLE
                    loadFragment(AddSections())
                }
                0-> {
                    loadFragment(CreateCourseFragment())
                    confirm.startAnimation(animationOut)
                    confirm.visibility=View.INVISIBLE
                    prev.startAnimation(animationOut)
                    prev.visibility=View.INVISIBLE
                }
            }
        })

    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.addCourseFrameLayout,fragment)
        transaction.commit()
    }
}