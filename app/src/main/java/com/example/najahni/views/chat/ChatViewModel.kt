package com.example.najahni.views.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.models.Enroll
import com.example.najahni.models.Message
import com.example.najahni.models.User
import com.example.najahni.models.UserWithMessage
import com.example.najahni.services.implementation.MessageService

class ChatViewModel : ViewModel(){
    var success = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var tokenSaved = MutableLiveData<String>()
    var contacts = MutableLiveData<List<User>>()

    fun getAllContact(token: String, action: (List<UserWithMessage>) -> Unit){
        MessageService.getAllContact(token,{code,list -> action(list!!)})
    }
}