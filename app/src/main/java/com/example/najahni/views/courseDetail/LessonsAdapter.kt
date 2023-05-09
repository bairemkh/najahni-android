package com.example.najahni.views.courseDetail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Lesson
import com.example.najahni.services.implementation.EnrollService
import com.example.najahni.utils.Consts
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.utils.SharedPrefsNajahni.SHARED_PREFS
import com.example.najahni.views.coursedetaillesson.videoplayer.VideoPlayerActivity

class LessonsAdapter(val list: List<Lesson>,val courseId: String, val open: Boolean): RecyclerView.Adapter<LessonsAdapter.ViewHolder> () {

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.lessonNameList)
        val index: TextView = itemView.findViewById(R.id.lessonIndexList)
        val duration: TextView = itemView.findViewById(R.id.lessonDurationList)
        val isLocked: ImageView = itemView.findViewById(R.id.lessonIsUnlockedList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
         return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val lesson = list[position]
                holder.title.text = lesson.title
                holder.index.text= "${position+1}"
                holder.duration.text="${lesson.duration /60} min and ${lesson.duration %60} sec"
                when(open){
                    true -> {
                        holder.isLocked.setImageResource(R.drawable.baseline_play_arrow_24)
                        holder.itemView.setOnClickListener {
                            val sharedPreferences = holder.itemView.context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                            EnrollService.progress(courseId,lesson.id!!,SharedPrefsNajahni.getToken(sharedPreferences)){code->
                                if(code==200){
                                    val intent = Intent(holder.itemView.context, VideoPlayerActivity::class.java)
                                    intent.putExtra(Consts.SELECTED_LESSON_INTENT, lesson)
                                    it.context.startActivity(intent)
                                }
                            }
                        }
                    }
                    false -> holder.isLocked.setImageResource(R.drawable.baseline_lock_24)
                }
    }
}