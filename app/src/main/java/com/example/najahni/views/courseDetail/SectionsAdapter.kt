package com.example.najahni.views.courseDetail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Section

class SectionsAdapter(val list: List<Section>,val isOwned:Boolean,val courseId:String) :
    RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionTitle: TextView = itemView.findViewById(R.id.sectionNameLessonCard)
        val numberOfLessons: TextView = itemView.findViewById(R.id.numberOfLessonsSectionCard)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.lessonsListRV)
    }
    private var onClicked:((Boolean)->Unit)?=null
    fun setOnClickedListener(action:(Boolean)->Unit){
        onClicked=action
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.section_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numberOfLessons.text =
            "${list[position].lessons.size} ${if (list[position].lessons.size == 1) "Lesson" else "Lessons"}"
        holder.sectionTitle.text = "${list[position].title}"
        holder.recyclerView.adapter = LessonsAdapter(
            list[position].lessons,
            courseId,
            isOwned
        ).apply {
            setOnClickedListener {
                onClicked?.invoke(it)
                Log.i("play","Clicked")
            }
        }



    }
}