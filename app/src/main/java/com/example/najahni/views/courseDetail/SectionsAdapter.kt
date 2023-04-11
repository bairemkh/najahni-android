package com.example.najahni.views.courseDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Section

class SectionsAdapter(val list:List<Section>): RecyclerView.Adapter<SectionsAdapter.ViewHolder> () {
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val sectionTitle: TextView = itemView.findViewById(R.id.sectionNameLessonCard)
        val numberOfLessons: TextView = itemView.findViewById(R.id.numberOfLessonsSectionCard)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.lessonsListRV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.section_card,parent,false)
        return SectionsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numberOfLessons.text = "${list[position].lessons.size} ${if (list[position].lessons.size==1) "Lesson" else "Lessons"}"
        holder.sectionTitle.text="${list[position].title}"
        holder.recyclerView.adapter = LessonsAdapter(list[position].lessons)
    }
}