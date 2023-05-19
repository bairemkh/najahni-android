package com.example.najahni.views.courses.fragments

import android.content.Intent
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
import com.example.najahni.views.courseDetail.CourseDetail
import com.example.najahni.views.coursedetaillesson.CourseDetailLessonActivity
import com.example.najahni.views.home.CourseAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class CourseEnrollAdapter (val courses : List<Enroll>) : RecyclerView.Adapter<CourseEnrollAdapter.ViewHolder> (){

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val courseimg: ImageView = itemView.findViewById(R.id.courseenrollcardimg)
        val title: TextView = itemView.findViewById(R.id.coursenameenrollid)
        val lessonsComp: TextView = itemView.findViewById(R.id.lessonsCompleted)
        val progress: CircularProgressIndicator = itemView.findViewById(R.id.progress_circular)
    }
    private var onClicked:((Int)->Unit)?=null
    fun setOnClickedListener(action:(Int)->Unit){
        onClicked=action
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
        holder.lessonsComp.text = "${course.courseid.lesson_number} lessons"
        holder.progress.progress = (holder.progress.max * course.progress).roundToInt()
        holder.itemView.setOnClickListener {
            onClicked?.invoke(position)
        }
    }
}