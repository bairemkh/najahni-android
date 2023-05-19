package com.example.najahni.views.chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.User
import com.example.najahni.models.UserWithMessage
import com.example.najahni.utils.Consts
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ContactAdapter(val contacts : List<UserWithMessage>) : RecyclerView.Adapter<ContactAdapter.ViewHolder> (){
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val image : CircleImageView = itemView.findViewById(R.id.userContactImage)
        val username : TextView = itemView.findViewById(R.id.userName)
        val lastmsg :  TextView = itemView.findViewById(R.id.temp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact,parent,false)
        return ContactAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contacts = contacts[position]
        Picasso.get().load(Consts.BASE_URL1 + contacts.user.image).into(holder.image)
        holder.username.text = contacts.user.firstname + " " + contacts.user.lastname
        holder.lastmsg.text = if (contacts.messages.last().senderid == CurrentUser._id) "You: " +contacts.messages.last().msgContent else contacts.messages.last().msgContent
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DiscussionActivity::class.java)
            intent.putExtra(Consts.SELECTED_CONTACT_INTENT,contacts)
            it.context.startActivity(intent)
        }
    }
}