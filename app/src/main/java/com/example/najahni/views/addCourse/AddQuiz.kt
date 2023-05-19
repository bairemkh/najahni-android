package com.example.najahni.views.addCourse


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.Quiz
import com.example.najahni.utils.Consts
import com.example.najahni.views.addCourse.adapters.EditQuizListAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.util.*

class AddQuiz(var course: Course) : Fragment() {
    lateinit var recyclerView:RecyclerView
    lateinit var adapter: EditQuizListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_add_quiz, container, false)
        recyclerView=view.findViewById(R.id.editQuizRV)
         adapter = EditQuizListAdapter(course.quiz){ quiz, position->
            val intent = Intent(view.context, EditQuiz::class.java)
            intent.putExtra(Consts.SELECTED_QUIZ_INTENT, quiz)
            intent.putExtra("indexQuest", position+1)
            startActivityForResult(intent, 200)}
        view.findViewById<ExtendedFloatingActionButton>(R.id.addQuizBtn).setOnClickListener {
            adapter.addQuiz(Quiz(UUID.randomUUID().toString(),"Question", mutableListOf("Proposition one"),0))
            adapter.notifyItemInserted(adapter.itemCount - 1)
        }
        recyclerView.adapter=adapter
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            val updatedQuiz = data!!.getSerializableExtra(Consts.SELECTED_QUIZ_INTENT) as Quiz
            Log.e("quiz","$updatedQuiz")
            course.quiz[course.quiz.indexOfFirst { q->q.id==updatedQuiz.id }]=updatedQuiz
            adapter.notifyDataSetChanged()
        }
    }
}