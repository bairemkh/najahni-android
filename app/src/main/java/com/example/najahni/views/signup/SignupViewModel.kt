package com.example.najahni.views.signup


import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.najahni.R
import com.example.najahni.models.User
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Role
import com.example.najahni.services.implementation.UserService


class SignupViewModel : ViewModel() {
    var canPass = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var fieldsMessage = MutableLiveData<String>()
    private var boolArray= MutableLiveData(BooleanArray(Field.values().size))
    private var values =MutableLiveData(Field.values().map { f->f.name }.toTypedArray())
    var selectedFields=MutableLiveData<ArrayList<Field>>(arrayListOf())

    fun check(
        name: TextView,
        lastName: TextView,
        email: TextView,
        password: TextView,
        confirmPass: TextView
    ) {
        listOf(name, lastName, email, password, confirmPass).forEach { x ->
            if (x.text.isEmpty()) {
                canPass.value = false
                message.value = "You must fill the empty text fields"
                return
            }
        }
        if (!password.text.toString().equals(confirmPass.text.toString())) {
            canPass.value = false
            return
        }
        canPass.value = true
    }
     fun openChoiceDialog(context:Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select an Option")
        builder.setMultiChoiceItems(values.value,boolArray.value
        ) { _, which, isChecked ->
            if(isChecked){
                selectedFields.value!!.add(Field.valueOf(values.value!![which]))
                fieldsMessage.value="${selectedFields.value!!.size} Fields Selected"
            }else{
                selectedFields.value!!.removeAt(which)
                fieldsMessage.value="${selectedFields.value!!.size} Fields Selected"
            }
        }
        /*builder.setPositiveButton("Done"){x,y->
            fieldsMessage.value="${selectedFields.value!!.size} Fields Selected"
        }*/
        builder.create().show()
    }
    fun register(user:User){
        UserService.create(user){res,code->
            canPass.value=res;
        }
    }
}