package com.example.najahni.views.chat

import androidx.lifecycle.ViewModel
import com.example.najahni.models.CurrentUser
import io.socket.client.Socket
import org.json.JSONObject
import java.util.UUID

class DiscussionViewModel : ViewModel() {

    lateinit var socket : Socket

    fun sendMessage(message: String,id:String){
        val messageJson = JSONObject().apply {
            put("msgContent", message)
            put("senderid", CurrentUser._id)
            put("receiverid", "641a71e0ea9a1fe69d1489b1")
            put("_id",  UUID.randomUUID().toString())

        }
        socket.emit("onMessage",messageJson)

    }

}