package com.example.najahni.views.profileTrainer.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.CurrentUser
import com.example.najahni.views.addCourse.AddCourseActivity
import com.example.najahni.views.editProfile.EditProfileActivity
import com.example.najahni.views.home.CourseAdapter
import com.example.najahni.views.home.HomeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CoursesTrainerFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_courses_trainer, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewCoursesTrainer)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getAllCourses{ list ->
            recycler.layoutManager = LinearLayoutManager(activity)
            recycler.adapter = CourseAdapter(list.filter { course: Course -> course.idOwner._id == CurrentUser._id && !course.isArchived })

        }
        view.findViewById<FloatingActionButton>(R.id.addCourseBtn).setOnClickListener {
            activity.let {
                val intent = Intent(it, AddCourseActivity::class.java)
                it?.startActivity(intent)
            }
        }
        return view
    }
}