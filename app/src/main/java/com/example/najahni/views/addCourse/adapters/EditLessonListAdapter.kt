package com.example.najahni.views.addCourse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Lesson
import com.example.najahni.models.Section

class EditLessonListAdapter(val list:MutableList<Lesson>,val onModify:(Lesson)->Unit) :RecyclerView.Adapter<EditLessonListAdapter.ViewHolder> (){
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val lessonNameEdit: TextView = itemView.findViewById(R.id.lessonNameEdit)
        val lessonIndex: TextView = itemView.findViewById(R.id.numLessonEdit)
        val lessonDurationEdit: TextView = itemView.findViewById(R.id.lessonDurationEdit)
        val deleteLessonBtn: ImageView = itemView.findViewById(R.id.deleteLessonBtn)
        val editLessonBtn: ImageView = itemView.findViewById(R.id.editLessonBtn)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditLessonListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_card_edit,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lessonNameEdit.text=list[position].title
        holder.lessonDurationEdit.text="${list[position].duration /60} min and ${list[position].duration %60} sec"
        holder.deleteLessonBtn.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
        holder.lessonIndex.text="${position+1}"
        holder.editLessonBtn.setOnClickListener {
            onModify(list[position])
        }
    }
    fun addLesson(lesson: Lesson){
        list.add(lesson)
    }
}