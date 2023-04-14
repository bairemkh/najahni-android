package com.example.najahni.views.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.enums.Field
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.enums.Level
import com.example.najahni.utils.Consts
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
    lateinit var filter:ChipGroup
    lateinit var allList:List<Course>
    val displayList: MutableLiveData<List<Course>> = MutableLiveData()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        Picasso.get().load(Consts.BASE_URL1 + CurrentUser.image).into(view.findViewById<CircleImageView>(R.id.circle_imageView))
        view.findViewById<TextView>(R.id.usernamehome).text =  CurrentUser.firstname + " " + CurrentUser.lastname
        filter = view.findViewById(R.id.filterHomeGroup)
        val listFilter = mutableListOf("All")
        listFilter.addAll(Field.values().map { it.name })
        listFilter.forEach {
            val chip= Chip(view.context)
            chip.text=it
            chip.isCheckable=true
            chip.isCheckedIconEnabled=false
            chip.setTextColor(resources.getColor(R.color.white))
            chip.chipBackgroundColor= ContextCompat.getColorStateList(view.context,R.color.filter_background_colors)
            filter.addView(chip)
        }
        Log.e("size","size = ${filter.size}")
        filter.setOnCheckedStateChangeListener { _, checkedIds ->
            checkedIds.forEach { t-> val sel =view.findViewById<Chip>(t)
            if(sel.text=="All"){
                displayList.value = allList
            }else{
                val filter= Field.values().find { f->f==Field.valueOf(sel.text.toString()) }
                displayList.value = allList.filter { course -> course.fields.contains(filter) }
            }
            }
        }
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
        val recyclerRecommendedCoursesAdapter = view.findViewById<RecyclerView>(R.id.RecommendedCoursesList)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getAllCourses{ list ->
            allList=list
            recycler.layoutManager = LinearLayoutManager(activity)
            recycler.adapter = CourseAdapter(list)
            recyclerRecommendedCoursesAdapter.adapter = RecommendedCoursesAdapter(list.subList(0,list.size-1))
        }
        displayList.observe(viewLifecycleOwner, Observer { myList ->
            recycler.adapter=CourseAdapter(myList)
        })


        return view
    }

}