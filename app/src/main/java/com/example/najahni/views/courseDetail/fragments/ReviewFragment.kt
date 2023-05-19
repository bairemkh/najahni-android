package com.example.najahni.views.courseDetail.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.utils.Consts
import com.example.najahni.views.chat.DiscussionActivity
import com.squareup.picasso.Picasso


class ReviewFragment(val course:Course) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_review, container, false)
        view.findViewById<ImageView>(R.id.startdiscussion).setOnClickListener {
            val intent = Intent(activity, DiscussionActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<TextView>(R.id.userNameCourseReview).text = "${course.idOwner.firstname} ${course.idOwner.lastname}"
        view.findViewById<TextView>(R.id.descriptionCourseReview).text = course.description
        Picasso.get().load(Consts.BASE_URL1 + course.idOwner.image).into(view.findViewById<ImageView>(R.id.userImgCourseReview))
        return view
    }
}