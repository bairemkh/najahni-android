package com.example.najahni.views.courses.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.Enroll
import com.example.najahni.utils.Consts
import com.example.najahni.views.home.CourseAdapter
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class CourseEnrollAdapter (val courses : List<Enroll>) : RecyclerView.Adapter<CourseEnrollAdapter.ViewHolder> (){

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val courseimg: ImageView = itemView.findViewById(R.id.courseenrollcardimg)
        val title: TextView = itemView.findViewById(R.id.coursenameenrollid)
        val progress: ProgressBar = itemView.findViewById(R.id.progress_circular)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_enroll_card,parent,false)
        return CourseEnrollAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        Picasso.get().load(Consts.BASE_URL1 + course.courseid.image).into(holder.courseimg)
        holder.title.text = course.courseid.title
        holder.progress.progress = course.progress.roundToInt() * 100
    }
}