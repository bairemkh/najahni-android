package com.example.najahni.views.courseDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Lesson
import com.example.najahni.models.Section
import com.example.najahni.views.home.CourseAdapter

class LessonsAdapter(val list:List<Lesson>): RecyclerView.Adapter<LessonsAdapter.ViewHolder> () {
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.lessonNameList)
        val index: TextView = itemView.findViewById(R.id.lessonIndexList)
        val duration: TextView = itemView.findViewById(R.id.lessonDurationList)
        val isLocked: ImageView = itemView.findViewById(R.id.lessonIsUnlockedList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_card,parent,false)
        return LessonsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LessonsAdapter.ViewHolder, position: Int) {
        val lesson = list[position]
        holder.title.text = lesson.title
        holder.index.text= "${position+1}"
        holder.duration.text="${lesson.duration/60} min and ${lesson.duration%60} sec"
    }
}