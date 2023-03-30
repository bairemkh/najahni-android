package com.example.najahni.views.profileTrainer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.najahni.R
import com.example.najahni.models.CurrentUser
import com.example.najahni.utils.Consts
import com.example.najahni.views.editProfile.EditProfileActivity
import com.example.najahni.views.profile.ProfileFragment
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ProfileTrainerFragment : Fragment() {

    private lateinit var tablayout : TabLayout
    private lateinit var viewPager : ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_trainer, container, false)
        view.findViewById<Toolbar>(R.id.toolbarprofiletrainer).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_more -> fragmentManager?.beginTransaction()?.replace(R.id.fram_container,ProfileFragment())?.commit()

            }
            true
        }
        Picasso.get().load(Consts.BASE_URL1 + CurrentUser.image).into(view.findViewById<CircleImageView>(R.id.profiletTrainerimg))
        view.findViewById<TextView>(R.id.usernameprofilet).text = CurrentUser.firstname + " " + CurrentUser.lastname
        view.findViewById<TextView>(R.id.emailprofilet).text = CurrentUser.email
        tablayout = view.findViewById(R.id.tablayout)
        viewPager = view.findViewById(R.id.viewLoader)
        tablayout.addTab(tablayout.newTab().setText("Courses"))
        tablayout.addTab(tablayout.newTab().setText("Archived"))
        tablayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = activity?.let { TabAdapter(it,childFragmentManager,tablayout.tabCount) }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}