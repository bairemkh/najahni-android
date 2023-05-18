package com.example.najahni.views.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.Quiz
import com.example.najahni.utils.Consts
import com.example.najahni.views.addCourse.AddCourseActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizInterface : AppCompatActivity() {
    lateinit var question:TextView
    lateinit var indexQuestion:TextView
    var answers:MutableMap<Int,Pair<Boolean,String>> = mutableMapOf()
    lateinit var props:LinearLayout
    var step:MutableLiveData<Int> = MutableLiveData(-1)
    lateinit var questions:MutableList<Quiz>
    private lateinit var timerProgressBar:ProgressBar
    private lateinit var animationOut: Animation
    lateinit var animationIn: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val course= intent.getSerializableExtra(Consts.SELECTED_COURSE_INTENT) as Course
        questions=course.quiz
        question=findViewById(R.id.questionQuiz)
        indexQuestion=findViewById(R.id.questionIndexQuiz)
        props=findViewById(R.id.listPropositions)
        timerProgressBar=findViewById(R.id.timerQuiz)
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                Log.e("timer","$secondsLeft seconds")
                timerProgressBar.progress= secondsLeft.toInt()
            }
            override fun onFinish() {
                for (i in step.value!! until questions.size){
                    answers[i]= Pair(false,questions[step.value!!].propositions[questions[step.value!!].answerIndex])
                }
                val intent = Intent(this@QuizInterface, QuizResult::class.java)
                intent.putExtra("answers", HashMap(answers))
                startActivity(intent)
                finish()
            }
        }
        timer.start()
        animationOut = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_out)
        animationIn=AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in)
        step.value=0
        questions.shuffle()
        step.observe(this, Observer {
                if(it<questions.size){
                    timerProgressBar.max=60
                    timerProgressBar.progress=0
                    loadQuiz(questions[step.value!!]){ choice,answer,list->
                        Log.e("is True","${answers.values.toList()}")
                        answers[step.value!!]=Pair(choice==answer,list[answer])
                        step.value= step.value!! +1
                    }
                }else{
                    Log.e("Hit end","end")
                    val intent = Intent(this@QuizInterface, QuizResult::class.java)
                    timer.cancel()
                    intent.putExtra("answers", HashMap(answers))
                    intent.putExtra("courseId", course.id)
                    startActivity(intent)
                    finish()
                }

        })

    }

    private fun loadQuiz(selectedQuiz: Quiz, onChoose:(Int, Int,List<String>)->Unit) {
            if (selectedQuiz!=questions[0]) {
                props.startAnimation(animationOut)
                props.visibility = View.INVISIBLE
            }
            val response = selectedQuiz.propositions[selectedQuiz.answerIndex]
            indexQuestion.text="Question ${step.value!!+1}"
            Log.e("answer",response)
            props.removeAllViews()
            selectedQuiz.propositions.shuffle()
            Log.e("list","======> ${selectedQuiz.propositions}")
            question.text=selectedQuiz.question
            selectedQuiz.propositions.forEachIndexed { index, s ->
                val cardView = CardView(this).apply {
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        val margin = 10
                        setMargins(margin, margin, margin, margin)
                        setPadding(margin, margin, margin, margin)
                    }
                    setOnClickListener {
                        Log.i("Answer", selectedQuiz.propositions.indexOf(response).toString())
                        onChoose(index,selectedQuiz.propositions.indexOf(response),selectedQuiz.propositions)
                    }
                }
                val text = TextView(this).apply {
                    gravity = Gravity.START
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        val margin = 20
                        setPadding(margin, margin, margin, margin)
                    }
                    background=null
                    text = s
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
                    typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)

                }
                cardView.addView(text)
                props.addView(cardView)
            }
            props.startAnimation(animationIn)
            props.visibility = View.VISIBLE

    }
}