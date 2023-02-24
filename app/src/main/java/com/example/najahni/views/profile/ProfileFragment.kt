package com.example.najahni.views.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.najahni.R
import com.example.najahni.models.CurrentUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_profile, container, false)
        Picasso.get().load("http://192.168.1.16:9090" + CurrentUser.image).into(view.findViewById<CircleImageView>(R.id.profileimg))
        view.findViewById<TextView>(R.id.usernameprifile).text = CurrentUser.firstname + " " + CurrentUser.lastname
        view.findViewById<TextView>(R.id.emailprofile).text = CurrentUser.email
        return view
    }
}