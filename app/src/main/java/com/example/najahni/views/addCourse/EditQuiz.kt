package com.example.najahni.views.addCourse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.najahni.R
import com.example.najahni.models.Quiz
import com.example.najahni.utils.Consts
import com.google.android.material.button.MaterialButton


class EditQuiz : AppCompatActivity() {
    lateinit var question: EditText
    private lateinit var questionIndex: TextView
    lateinit var addProp: MaterialButton
    private lateinit var props: LinearLayout
    lateinit var selectedQuiz:Quiz
    var answer= MutableLiveData(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_quiz)
        selectedQuiz = intent.getSerializableExtra(Consts.SELECTED_QUIZ_INTENT) as Quiz
        question = findViewById(R.id.questionFieldEditQuiz)
        questionIndex = findViewById<TextView?>(R.id.questionIndexEditQuiz).apply {
            this.text = "Question ${intent.getIntExtra("indexQuest", 0)}"
        }
        addProp = findViewById<MaterialButton?>(R.id.addPropEditQuiz)
        props = findViewById(R.id.propositionsEditQuiz)
        selectedQuiz.propositions.forEachIndexed { i,q ->
                props.addView(addPropCard(q,selectedQuiz,i))
        }
        findViewById<Button>(R.id.addPropEditQuiz).setOnClickListener {
            selectedQuiz.propositions.add("proposition ${selectedQuiz.propositions.size+1}")
            props.addView(addPropCard("proposition ${selectedQuiz.propositions.size}",selectedQuiz,selectedQuiz.propositions.size))
        }
        findViewById<Button>(R.id.confirmEditQuizBtn).setOnClickListener {
            val intent = Intent()
            selectedQuiz.question=question.text.toString()
            Log.e("selected quiz","$selectedQuiz")
            intent.putExtra(Consts.SELECTED_QUIZ_INTENT, selectedQuiz)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
    private fun addPropCard(text: String, quiz: Quiz, index:Int): CardView {
        val cardView = CardView(this).apply {
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                val margin = 10
                setMargins(margin, margin, margin, margin)
            }
            if (index==quiz.answerIndex){
                this.setCardBackgroundColor(resources.getColor(R.color.secondary_color))
            }else{
                this.setCardBackgroundColor(resources.getColor(R.color.white))
            }
            setOnClickListener {
                answer.value=index
                quiz.answerIndex=index
                this.setCardBackgroundColor(resources.getColor(R.color.secondary_color))
            }
            answer.observe(this@EditQuiz, Observer { selection->
                if (selection!=index)
                    this.setCardBackgroundColor(resources.getColor(R.color.white))
            })
        }

        val editView = EditText(this).apply {
            gravity = Gravity.START
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                val margin = 20
                setMargins(margin, 0, margin, 0)
            }
            setText(text)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
            typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
            this.doAfterTextChanged { newValue->
                quiz.propositions[index] = newValue.toString()
            }
        }

        cardView.addView(editView)
        return cardView
    }
}