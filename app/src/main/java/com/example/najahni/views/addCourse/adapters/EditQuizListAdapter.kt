package com.example.najahni.views.addCourse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Quiz

class EditQuizListAdapter(val list:MutableList<Quiz>,val onEdit:(Quiz,Int)->Unit): RecyclerView.Adapter<EditQuizListAdapter.ViewHolder> () {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val questionTitle: TextView = itemView.findViewById(R.id.questionTitleQuizCard)
        val numberOfProps: TextView = itemView.findViewById(R.id.numberOfPropsQuizCard)
        val answerQuizCard: TextView = itemView.findViewById(R.id.answerQuizCard)
        val deleteQuestionQuizCard: ImageView = itemView.findViewById(R.id.deleteQuestionQuizCard)
        val editQuestionQuizCard: ImageView = itemView.findViewById(R.id.editQuestionQuizCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.edit_quiz_card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz= list[position]
        holder.questionTitle.text = quiz.question
        holder.numberOfProps.text = "${quiz.propositions.size} propositions"
        holder.answerQuizCard.text = "Answer: ${quiz.propositions[quiz.answerIndex]}"
        holder.deleteQuestionQuizCard.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
        holder.editQuestionQuizCard.setOnClickListener {
            onEdit(quiz,position)
        }
    }
    fun addQuiz(item: Quiz) {
        list.add(item)
    }
}