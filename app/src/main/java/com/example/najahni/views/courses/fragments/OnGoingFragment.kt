package com.example.najahni.views.courses.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Enroll
import com.example.najahni.utils.Consts
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.views.coursedetaillesson.CourseDetailLessonActivity
import com.example.najahni.views.courses.CoursesViewModel
import com.example.najahni.views.home.CourseAdapter
import com.example.najahni.views.home.HomeViewModel


class OnGoingFragment : Fragment() {
    lateinit var viewModel: CoursesViewModel
    var onChanged:((Enroll)->Unit)?=null
    fun onChangedListener(onChangedAction:((Enroll)->Unit)){
        onChanged=onChangedAction
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences: SharedPreferences =
            activity?.getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)!!
        val token = SharedPrefsNajahni.getToken(sharedPreferences)
        val view = inflater.inflate(R.layout.fragment_on_going, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerViewOnGoing)
        viewModel = ViewModelProvider(this)[CoursesViewModel::class.java]
        viewModel.getMyCourses( token) { list ->
            recycler.layoutManager = LinearLayoutManager(activity)
            recycler.adapter = CourseEnrollAdapter(list.filter { enroll -> enroll.progress < 1 }).apply {
                setOnClickedListener {
                    val intent = Intent(context, CourseDetailLessonActivity::class.java)
                    intent.putExtra(Consts.SELECTED_COURSELESSON_INTENT, list[it])
                    startActivityForResult(intent,200)
                }
            }
        }
        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("codes","requestCode:$requestCode, resultCode:$resultCode")
        if (requestCode == 200) {
            Log.e("returned","${data!!.getSerializableExtra(Consts.SELECTED_COURSELESSON_INTENT) as Enroll}")
           // onChanged?.invoke(data!!.getSerializableExtra(Consts.SELECTED_COURSELESSON_INTENT) as Enroll)
            /*onChangedListener{enroll->
                Log.e("test","Progress ${enroll.progress}")
            }*/
            /* val enroll = data!!.getSerializableExtra(Consts.SELECTED_QUIZ_INTENT) as Enroll
             Log.e("Enroll","$enroll")
             course.quiz[course.quiz.indexOfFirst { q->q.id==updatedQuiz.id }]=updatedQuiz
             adapter.notifyDataSetChanged()*/
        }
    }
}