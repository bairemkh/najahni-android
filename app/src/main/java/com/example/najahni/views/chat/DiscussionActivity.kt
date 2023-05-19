package com.example.najahni.views.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.models.Course
import com.example.najahni.models.Message
import com.example.najahni.models.User
import com.example.najahni.models.UserWithMessage
import com.example.najahni.utils.Consts
import com.example.najahni.utils.SocketHandler
import com.example.najahni.views.login.LoginViewModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DiscussionActivity : AppCompatActivity() {
    lateinit var snedMessage: EditText
    lateinit var sendBtn: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discussion)
        val selectedContact = intent.getSerializableExtra(Consts.SELECTED_CONTACT_INTENT) as UserWithMessage

       val intent = Intent(this, SocketHandler::class.java)
       startService(intent)
        Picasso.get().load(Consts.BASE_URL1 + selectedContact.user.image).into(findViewById<CircleImageView>(R.id.imgProfile))

        findViewById<TextView>(R.id.tvUserName).text = selectedContact.user.firstname + " " + selectedContact.user.lastname
        val recycler = findViewById<RecyclerView>(R.id.chatRecyclerView)
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        recycler.scrollToPosition(selectedContact.messages.size - 1)
        findViewById<ImageButton>(R.id.btnSendMessage).setOnClickListener {
            val message = findViewById<EditText>(R.id.etMessage).text
            SocketHandler.sendMessage(message.toString(),selectedContact.user._id.toString())
            findViewById<EditText>(R.id.etMessage).text.clear()

        }
        val adapter = MessageAdapter(selectedContact.messages.toMutableList(),selectedContact.user)


        SocketHandler.initSocket(selectedContact.user){msg ->
            runOnUiThread{
            adapter.addMessage(msg)
            adapter.notifyItemInserted(adapter.itemCount-1)}
        }

            recycler.adapter = adapter


    }
}