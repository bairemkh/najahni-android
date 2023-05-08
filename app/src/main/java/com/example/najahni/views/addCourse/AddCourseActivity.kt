package com.example.najahni.views.addCourse


import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.enums.Level
import com.example.najahni.services.implementation.CourseService
import com.example.najahni.utils.ApiResponseHandling
import com.example.najahni.utils.Consts
import com.example.najahni.utils.Consts.EDIT_MODE
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.utils.SharedPrefsNajahni.SHARED_PREFS
import java.io.File
import java.util.*


class AddCourseActivity : AppCompatActivity() {
    private var pageIndex:MutableLiveData<Int> = MutableLiveData(0)
    lateinit var prev:TextView
    lateinit var nextPage:TextView
    lateinit var confirm:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        val editMode=intent.getIntExtra(EDIT_MODE,0)
        var course:Course?=null
        if(editMode==1) {
            course = Course(
                UUID.randomUUID().toString(),
                "",
                "",
                mutableListOf(),
                0,
                Level.Beginner,
                "",
                false,
                0.toDouble(),
                CurrentUser.getCurrentUser()!!,
                mutableListOf(),
                false,
                0,
                mutableListOf()
            )
        }else{
             course = intent.getSerializableExtra(Consts.SELECTED_COURSE_INTENT) as Course
        }
        val animationOut: Animation = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_out)
        val animationIn: Animation = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in)
        prev=findViewById<TextView?>(R.id.pervBtnAddCourse).apply {
            this.visibility= View.INVISIBLE
        }
        var uri: MutableLiveData<Uri> =MutableLiveData()
        nextPage=findViewById(R.id.nextBtnAddCourse)
        confirm=findViewById<TextView?>(R.id.confirmBtnAddCourse).apply {
            this.visibility= View.INVISIBLE
        }
        loadFragment(CreateCourseFragment(course,uri))
        prev.setOnClickListener {
            pageIndex.value=pageIndex.value!!-1
            Log.e("click","prev")
        }
        nextPage.setOnClickListener {
            pageIndex.value=pageIndex.value!!+1
        }
        pageIndex.observe(this, Observer { index->
            when (index){
                2-> {
                    nextPage.startAnimation(animationOut)
                    nextPage.visibility=View.INVISIBLE
                    loadFragment(AddQuiz(course))
                }
                1-> {
                    nextPage.startAnimation(animationIn)
                    nextPage.visibility=View.VISIBLE
                    confirm.startAnimation(animationIn)
                    confirm.visibility=View.VISIBLE
                    prev.startAnimation(animationIn)
                    prev.visibility= View.VISIBLE
                    loadFragment(AddSections(course))
                }
                0-> {
                    loadFragment(CreateCourseFragment(course,uri))
                    confirm.startAnimation(animationOut)
                    confirm.visibility=View.INVISIBLE
                    prev.startAnimation(animationOut)
                    prev.visibility=View.INVISIBLE
                }
            }
        })
        confirm.setOnClickListener {
            Log.e("course","$course")
            Log.e("uri","$uri")
            if(editMode==1){
                var canPass=true
                listOf(course.title,course.description).forEach { element->
                    if (element.isEmpty())
                        canPass=false
                }
                if((!canPass)){
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Error while creating a course")
                    alertDialogBuilder.setMessage("Missing the title or the description , please return and fill them")
                    alertDialogBuilder.setNegativeButton("Ok"
                    ) { _, _ ->
                    }
                    alertDialogBuilder.create().show()
                    return@setOnClickListener
                }
                if((course.fields.size==0)||(course.sections.size==0)){
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Adding Course Confirmation")
                    alertDialogBuilder.setMessage("Are you sure to create this course without adding any section or quiz ?")
                    alertDialogBuilder.setPositiveButton("yes, continue"
                    ) { _, _ ->
                        createNewCourse(course,uri.value!!){
                            finish()
                        }
                    }
                    alertDialogBuilder.create().show()
                }else{
                    createNewCourse(course,uri.value!!){
                        finish()
                    }
                }
            }else{
                updateCourse(course){
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Confirming update")
                    alertDialogBuilder.setMessage("You have update  ${course.title} course")
                    alertDialogBuilder.setPositiveButton("Okay"
                    ) { _, _ ->
                        finish()
                    }
                    alertDialogBuilder.create().show()
                }
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.addCourseFrameLayout,fragment)
        transaction.commit()
    }

    private fun createNewCourse(course: Course, uri: Uri, onSucc:()->Unit){
        val file=File(getRealPathFromURI(uri))
        val sharedPreferences= getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        CourseService.create(SharedPrefsNajahni.getToken(sharedPreferences),course,file,object:ApiResponseHandling{
            override fun onSuccess(data: Any) {
                val courseNew = data as Course
                courseNew.sections = course.sections
                courseNew.quiz = course.quiz
                courseNew.fields=course.fields
                updateCourse(courseNew){ done->
                    if(done) {
                        onSucc()
                    }
                }
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                val alertDialogBuilder = AlertDialog.Builder(this@AddCourseActivity)
                alertDialogBuilder.setTitle("Error while creating a course")
                alertDialogBuilder.setMessage("A server error have occurred ($errorMessage)")
                alertDialogBuilder.setNegativeButton("Ok"
                ) { _, _ ->
                }
                alertDialogBuilder.create().show()
            }

            override fun onFailure(errorMessage: String) {
                val alertDialogBuilder = AlertDialog.Builder(this@AddCourseActivity)
                alertDialogBuilder.setTitle("Error while creating a course")
                alertDialogBuilder.setMessage("Please check your internet Connection")
                alertDialogBuilder.setNegativeButton("okay",null)
                alertDialogBuilder.create().show()
            }

        })
    }
    private fun updateCourse(course:Course, onSucc:(Boolean)->Unit){
        val sharedPreferences= getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        CourseService.update(SharedPrefsNajahni.getToken(sharedPreferences),course){succ->
            onSucc(succ)
        }
    }
    private fun getRealPathFromURI(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver?.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return "Error"
    }
}