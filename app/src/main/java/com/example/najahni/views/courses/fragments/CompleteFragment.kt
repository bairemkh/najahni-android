package com.example.najahni.views.courses.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.views.courses.CoursesViewModel


class CompleteFragment : Fragment() {
    lateinit var viewModel: CoursesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_complete, container, false)
        val sharedPreferences: SharedPreferences =
            activity?.getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)!!
        val token = SharedPrefsNajahni.getToken(sharedPreferences)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewcomplete)
        viewModel = ViewModelProvider(this)[CoursesViewModel::class.java]
        viewModel.getMyCourses( token) { list ->
            recycler.layoutManager = LinearLayoutManager(activity)
            recycler.adapter = CourseEnrollAdapter(list.filter { enroll -> enroll.progress == 1f })
        }
        return view
    }
}