package com.example.najahni.views.profileTrainer

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.najahni.views.profileTrainer.fragment.ArchivedCoursesFragment
import com.example.najahni.views.profileTrainer.fragment.CoursesTrainerFragment

internal class TabAdapter (var context : Context , fragmentManager: FragmentManager, var tabCount: Int) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                CoursesTrainerFragment()
            }
            1 -> {
                ArchivedCoursesFragment()
            }
            else -> CoursesTrainerFragment()
        }
    }
}