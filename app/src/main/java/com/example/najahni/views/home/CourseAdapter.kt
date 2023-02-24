package com.example.najahni.views.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.squareup.picasso.Picasso

class CourseAdapter (val courses : List<Course>) : RecyclerView.Adapter<CourseAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return courses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
        val course = courses[position]

        Picasso.get().load("https://kotlinlang.org/assets/images/twitter/general.png").into(holder.courseimg)
        holder.title.text = course.title
        holder.price.text = course.price.toString()
        holder.rating.text = "5"
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val courseimg: ImageView = itemView.findViewById(R.id.coursecardimg)
        val title: TextView = itemView.findViewById(R.id.coursenameid)
        val price: TextView = itemView.findViewById(R.id.courseprice)
        val rating: TextView = itemView.findViewById(R.id.courseprice)
    }
}