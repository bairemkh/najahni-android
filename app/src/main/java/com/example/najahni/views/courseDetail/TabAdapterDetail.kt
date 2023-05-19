package com.example.najahni.views.courseDetail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.najahni.models.Course
import com.example.najahni.views.courseDetail.fragments.LessonsFragment
import com.example.najahni.views.courseDetail.fragments.ReviewFragment

internal class TabAdapterDetail (var context : Context, fragmentManager: FragmentManager, var tabCount: Int,val course:Course) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                LessonsFragment(course)
            }
            1 -> {
                ReviewFragment(course)
            }
            else -> LessonsFragment(course)
        }
    }
}