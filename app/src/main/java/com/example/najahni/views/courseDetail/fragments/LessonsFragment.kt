package com.example.najahni.views.courseDetail.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.views.courseDetail.LessonsAdapter

class LessonsFragment(val course: Course) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lessons, container, false)
        val recView= view.findViewById<RecyclerView>(R.id.recyclerViewLessonsCourseDetail)
        Log.e("List ======",course.sections.toString())
        recView.adapter = LessonsAdapter(course.sections)
        return view
    }
}