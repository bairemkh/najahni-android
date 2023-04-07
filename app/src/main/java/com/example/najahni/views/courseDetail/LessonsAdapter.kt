package com.example.najahni.views.courseDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Lesson
import com.example.najahni.models.Section

class LessonsAdapter(val list:List<Section>): RecyclerView.Adapter<LessonsAdapter.ViewHolder> () {
    val SECTION_TYPE=0
    val COURSE_TYPE=1

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.lessonNameList)
        val index: TextView = itemView.findViewById(R.id.lessonIndexList)
        val duration: TextView = itemView.findViewById(R.id.lessonDurationList)
        val isLocked: ImageView = itemView.findViewById(R.id.lessonIsUnlockedList)
        var sectionName: TextView = itemView.findViewById(R.id.sectionNameLessonCard)
        var lesson: LinearLayout = itemView.findViewById(R.id.cardLessonInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
         return list.sumBy { it.lessons.size } + list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      /*  when (holder.itemViewType) {
            SECTION_TYPE -> {
                val section = list[position / 2]
                holder.lesson.visibility = View.GONE
                holder.sectionName.visibility = View.VISIBLE
                holder.sectionName.text = section.title
            }
            COURSE_TYPE -> {
                val lesson = getLesson(position)
                holder.lesson.visibility = View.VISIBLE
                holder.sectionName.visibility = View.GONE
                holder.title.text = lesson.title
                holder.index.text= "${position+1}"
                holder.duration.text="${lesson.duration /60} min and ${lesson.duration %60} sec"
            }
        }*/
    }
   /* override fun getItemViewType(position: Int): Int {
        /*return if (position % 2 == 0) {
            SECTION_TYPE
        } else {
            COURSE_TYPE
        }*/
        return 0
    }*/
    private fun getLesson(position: Int): Lesson {
        val index = position / 2
        var count = 0
        return list.fold(null as Lesson?) { acc, section ->
            acc
                ?: if (count + section.lessons.size > index) {
                    section.lessons[index - count]
                } else {
                    count += section.lessons.size + 1
                    null
                }
        } ?: throw IllegalArgumentException("Invalid position")
    }
}