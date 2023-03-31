package com.example.najahni.views.courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.najahni.R
import com.example.najahni.views.profileTrainer.TabAdapter
import com.google.android.material.tabs.TabLayout

class CoursesFragment : Fragment() {

    private lateinit var tablayout : TabLayout
    private lateinit var viewPager : ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_courses, container, false)
        tablayout = view.findViewById(R.id.tablayoutcourses)
        viewPager = view.findViewById(R.id.viewLoadercourses)
        tablayout.addTab(tablayout.newTab().setText("OnGoing"))
        tablayout.addTab(tablayout.newTab().setText("Completed"))
        tablayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = activity?.let { TabAdapterEnroll(it,childFragmentManager,tablayout.tabCount) }
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        return view
    }

}