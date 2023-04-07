package com.example.najahni.views.home

import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.utils.Consts
import com.example.najahni.utils.Consts.SELECTED_COURSE_INTENT
import com.example.najahni.views.courseDetail.CourseDetail
import com.squareup.picasso.Picasso

class CourseAdapter (val courses : List<Course>) : RecyclerView.Adapter<CourseAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        Picasso.get().load(Consts.BASE_URL1 + course.image).into(holder.courseimg)
        holder.title.text = course.title
        holder.price.text = if (course.isPaid) course.price.toString() + "DT" else "free"
        holder.rating.text = "5"
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CourseDetail::class.java)
            intent.putExtra(SELECTED_COURSE_INTENT, course)
            it.context.startActivity(intent)
        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val courseimg: ImageView = itemView.findViewById(R.id.coursecardimg)
        val title: TextView = itemView.findViewById(R.id.coursenameid)
        val price: TextView = itemView.findViewById(R.id.courseprice)
        val rating: TextView = itemView.findViewById(R.id.courserating)
    }
}