package com.example.najahni.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.enums.Field
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.enums.Level
import com.example.najahni.utils.Consts
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        Picasso.get().load(Consts.BASE_URL1 + CurrentUser.image).into(view.findViewById<CircleImageView>(R.id.circle_imageView))
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getAllCourses{ list ->
            recycler.layoutManager = LinearLayoutManager(activity)
            recycler.adapter = CourseAdapter(list)
        }

        return view
    }

}