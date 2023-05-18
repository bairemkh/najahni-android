package com.example.najahni.views.quiz

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.services.implementation.EnrollService
import com.example.najahni.utils.SharedPrefsNajahni

class QuizResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)
        val answers= intent.getSerializableExtra("answers") as? MutableMap<Int, Pair<Boolean, String>>
        val courseId= intent.getStringExtra("courseId")
        val result= answers!!.values.fold(0) { acc, pair ->
            acc + if (pair.first) 1 else 0
        } > (answers.size / 2)
        val parentLayout = findViewById<LinearLayout>(R.id.resultAnswers)
        findViewById<TextView>(R.id.resultTitle).setText(if (result) R.string.congratulations_you_passed_the_quiz else R.string.refused_quiz)
        findViewById<TextView>(R.id.resultDesc).setText(if (result) R.string.congratulations_we_re_so_proud_of_you else R.string.refused_quiz_desc)
        findViewById<ImageView>(R.id.resultImage).setImageResource(if(result)R.drawable.congrats else R.drawable.failure)
        findViewById<Button>(R.id.goHomeBtn).setOnClickListener {
            if(result){
                val sharedPreferences= getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)
                EnrollService.sendCertificate(courseId!!,SharedPrefsNajahni.getToken(sharedPreferences)){code->
                    if(code==200){
                        startActivity(Intent(this@QuizResult,MainActivity::class.java))
                        finish()
                    }
                }
            }else{
                startActivity(Intent(this@QuizResult,MainActivity::class.java))
                finish()
            }

        }
        answers.forEach {entry ->
            val linearLayout = LinearLayout(this)
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.setPadding(15.dpToPx(), 15.dpToPx(), 15.dpToPx(), 15.dpToPx())

            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(
                30.dpToPx(),
                30.dpToPx()
            )
            imageView.setImageResource(if (entry.value.first) R.drawable.baseline_check_24 else R.drawable.baseline_close_24)
            imageView.setColorFilter(if (entry.value.first) ContextCompat.getColor(this, R.color.secondary_color)else ContextCompat.getColor(this, R.color.danger))

            val textView = TextView(this)
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView.text = "The answer of question ${entry.key+1} is : ${entry.value.second}"
            textView.textSize = 18f
            textView.setTextColor(if (entry.value.first) ContextCompat.getColor(this, R.color.secondary_color)else ContextCompat.getColor(this, R.color.danger))
            textView.setPadding(10.dpToPx(), 0, 0, 0)
            textView.gravity = Gravity.CENTER_VERTICAL

            linearLayout.addView(imageView)
            linearLayout.addView(textView)

            parentLayout.addView(linearLayout)
        }
    }
    fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}