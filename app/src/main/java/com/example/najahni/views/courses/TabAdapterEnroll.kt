package com.example.najahni.views.courses

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.najahni.views.courseDetail.fragments.LessonsFragment
import com.example.najahni.views.courseDetail.fragments.ReviewFragment
import com.example.najahni.views.courses.fragments.CompleteFragment
import com.example.najahni.views.courses.fragments.OnGoingFragment

class TabAdapterEnroll (var context : Context, fragmentManager: FragmentManager, var tabCount: Int) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                OnGoingFragment()
            }
            1 -> {
                CompleteFragment()
            }
            else -> OnGoingFragment()
        }
    }
}