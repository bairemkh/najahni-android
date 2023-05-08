package com.example.najahni.views.courseDetail.fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.utils.Consts
import com.example.najahni.views.addCourse.AddCourseActivity
import com.example.najahni.views.courseDetail.LessonsAdapter
import com.example.najahni.views.courseDetail.SectionsAdapter
import com.example.najahni.views.quiz.QuizInterface

class LessonsFragment(val course: Course) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lessons, container, false)
        val recView= view.findViewById<RecyclerView>(R.id.recyclerViewLessonsCourseDetail)
        Log.e("List ======",course.sections.toString())
        recView.adapter = SectionsAdapter(course.sections)
        view.findViewById<View>(R.id.quizCard).apply {
            this.findViewById<TextView>(R.id.lessonIndexList).apply {
                text = "Q"
                backgroundTintList= ColorStateList.valueOf(view.resources.getColor(R.color.secondary_color))
                setTextColor(view.resources.getColor(R.color.secondary_color))
            }
            this.findViewById<TextView>(R.id.lessonNameList).text = "Quiz"
            this.findViewById<TextView>(R.id.lessonDurationList).text = "${course.quiz.size} Questions"
            if(course.quiz.size==0){
                this.visibility=View.GONE
            }else{
                this.visibility=View.VISIBLE
            }
            this.findViewById<ImageView>(R.id.lessonIsUnlockedList).apply {
                backgroundTintList= ColorStateList.valueOf(view.resources.getColor(R.color.secondary_color))
                imageTintList=ColorStateList.valueOf(view.resources.getColor(R.color.secondary_color))
                setOnClickListener {
                    val intent = Intent(it.context, QuizInterface::class.java)
                    intent.putExtra(Consts.SELECTED_COURSE_INTENT, course)
                    it.context!!.startActivity(intent)
                }
            }
        }
        return view
    }
}