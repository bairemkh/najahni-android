package com.example.najahni.views.coursedetaillesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.Section
import com.example.najahni.utils.Consts
import com.example.najahni.views.courseDetail.SectionsAdapter

class CourseDetailLessonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail_lesson)

        val recView = findViewById<RecyclerView>(R.id.recyclerViewcourselessondetail)
        val selectedCourse = intent.getSerializableExtra(Consts.SELECTED_COURSELESSON_INTENT) as Course

        recView.adapter = SectionsAdapter(selectedCourse.sections)
    }
}