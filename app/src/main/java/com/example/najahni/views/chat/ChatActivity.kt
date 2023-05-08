package com.example.najahni.views.chat

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.najahni.R
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.utils.SocketHandler
import java.net.Socket

class ChatActivity : AppCompatActivity() {
    lateinit var viewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val sharedPreferences: SharedPreferences =
            applicationContext.getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)!!
        val token = SharedPrefsNajahni.getToken(sharedPreferences)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        val recycler = findViewById<RecyclerView>(R.id.userRecyclerView)
        viewModel.getAllContact(token){ list ->
            recycler.layoutManager = LinearLayoutManager(applicationContext)
            recycler.adapter = ContactAdapter(list)
        }
    }
}