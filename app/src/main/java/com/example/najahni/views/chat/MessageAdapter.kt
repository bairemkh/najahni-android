package com.example.najahni.views.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.Message
import com.example.najahni.models.User
import com.example.najahni.utils.Consts
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter(val messages : MutableList<Message>, val receiver: User) : RecyclerView.Adapter<MessageAdapter.ViewHolder> () {



    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val image : CircleImageView = itemView.findViewById(R.id.userImage)
        val messagesender : LinearLayout = itemView.findViewById(R.id.myMessageitem)
        val messagereceiver : LinearLayout = itemView.findViewById(R.id.senderMessageitem)
        val myMessage: TextView = itemView.findViewById(R.id.myMessage)
        val senderMessage: TextView = itemView.findViewById(R.id.tvMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_me,parent,false)
        return MessageAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messages = messages[position]
        Picasso.get().load(Consts.BASE_URL1 + receiver.image).into(holder.image)
        if (messages.senderid == CurrentUser._id){
            holder.myMessage.text = messages.msgContent
            holder.messagereceiver.visibility = View.GONE

        }else{
            holder.senderMessage.text = messages.msgContent
            holder.messagesender.visibility = View.GONE
        }


    }

    fun addMessage(message: Message){
        messages.add(message)
        Log.e("messages ==========",messages.toString())
    }

}