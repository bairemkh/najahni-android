package com.example.najahni.views.coursedetaillesson

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.Enroll
import com.example.najahni.models.Section
import com.example.najahni.utils.Consts
import com.example.najahni.views.courseDetail.SectionsAdapter
import com.example.najahni.views.quiz.QuizInterface

class CourseDetailLessonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail_lesson)

        val recView = findViewById<RecyclerView>(R.id.recyclerViewcourselessondetail)
        var selectedEnroll = intent.getSerializableExtra(Consts.SELECTED_COURSELESSON_INTENT) as Enroll
        val selectedCourse = selectedEnroll.courseid
        Log.e("sections","${selectedCourse.sections}")
        recView.adapter = SectionsAdapter(selectedCourse.sections,true,selectedCourse.id!!).apply {
            setOnClickedListener {
                Log.e("hello","in clicked lesson $it")
                if(it){
                    Log.e("before","${selectedEnroll.progress}")
                    selectedEnroll.progress = selectedEnroll.progress+(1/selectedEnroll.courseid.lesson_number)
                    Log.e("after" ,"${selectedEnroll.progress}")
                }
            }
        }
        findViewById<View>(R.id.quizCard).apply {
            this.findViewById<TextView>(R.id.lessonIndexList).apply {
                text = "Q"
                backgroundTintList= ColorStateList.valueOf(resources.getColor(R.color.secondary_color))
                setTextColor(resources.getColor(R.color.secondary_color))
            }
            this.findViewById<TextView>(R.id.lessonNameList).text = "Quiz"
            this.findViewById<TextView>(R.id.lessonDurationList).text = "${selectedCourse.quiz.size} Questions"
            if(selectedCourse.quiz.size==0){
                this.visibility= View.GONE
            }else{
                this.visibility= View.VISIBLE
            }
            this.findViewById<ImageView>(R.id.lessonIsUnlockedList).apply {
                var locked=selectedEnroll.progress<selectedCourse.lesson_number/2
                setImageResource(if(locked) R.drawable.baseline_play_arrow_24 else R.drawable.baseline_lock_24)
                backgroundTintList= ColorStateList.valueOf(resources.getColor(R.color.secondary_color))
                imageTintList= ColorStateList.valueOf(resources.getColor(R.color.secondary_color))
                setOnClickListener {
                    if(locked){
                        val intent = Intent(it.context, QuizInterface::class.java)
                        intent.putExtra(Consts.SELECTED_COURSE_INTENT, selectedCourse)
                        it.context!!.startActivity(intent)
                    }
                }
            }
        }
        intent.putExtra(Consts.SELECTED_COURSELESSON_INTENT,selectedEnroll)
        setResult(RESULT_OK, intent)
    }
}