package com.example.najahni.views.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.models.CurrentUser
import com.example.najahni.utils.Consts
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.views.editProfile.EditProfileActivity
import com.example.najahni.views.login.LoginView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_profile, container, false)
        Picasso.get().load(Consts.BASE_URL1 + CurrentUser.image).into(view.findViewById<CircleImageView>(R.id.profileimg))
        view.findViewById<TextView>(R.id.usernameprifile).text = CurrentUser.firstname + " " + CurrentUser.lastname
        view.findViewById<TextView>(R.id.emailprofile).text = CurrentUser.email
        view.findViewById<LinearLayout>(R.id.editprofile).setOnClickListener {
            activity.let {
                val intent = Intent(it,EditProfileActivity::class.java)
                it?.startActivity(intent)
            }
        }
        view.findViewById<LinearLayout>(R.id.logout).setOnClickListener {
            activity.let {
                val sharedPreferences: SharedPreferences = it?.getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)!!
                SharedPrefsNajahni.setToken(sharedPreferences, "")
                val intent = Intent(it,LoginView::class.java)
                it?.startActivity(intent)
                it?.finish()
            }
        }
        return view
    }
}