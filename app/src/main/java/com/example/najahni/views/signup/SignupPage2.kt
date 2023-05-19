package com.example.najahni.views.signup


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.models.User
import com.example.najahni.models.enums.Field
import com.example.najahni.models.enums.Role
import com.example.najahni.utils.Consts
import com.example.najahni.views.login.LoginView
import com.example.najahni.views.login.LoginViewModel

class SignupPage2 : AppCompatActivity() {
    private lateinit var number:TextView
    private  var boolArray= BooleanArray(Field.values().size)
    private var values =Field.values().map { f->f.name }.toTypedArray()
    private var selectedFields:ArrayList<Field> = arrayListOf()
    private var selectedRole = Role.Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page2)
        val student =findViewById<CardView>(R.id.optionStudent)
        val trainer =findViewById<CardView>(R.id.optionTrainer)
        chooseRole(student,trainer)
        val viewModel = ViewModelProvider(this)[SignupViewModel::class.java]
        student.setOnClickListener {
            chooseRole(student,trainer)
            selectedRole =Role.Student
        }
        trainer.setOnClickListener {
            chooseRole(trainer,student)
            selectedRole =Role.Trainer
        }
        number = findViewById(R.id.selectedFields)
        findViewById<LinearLayout>(R.id.openFieldOptions).setOnClickListener {
            viewModel.openChoiceDialog(this)
        }
        viewModel.fieldsMessage.observe(this, Observer {
            number.text = it
        })
        findViewById<Button>(R.id.createAccountBtn).setOnClickListener {
            Log.e("intent 2","password == ${intent.getStringExtra(Consts.USER_PASSWORD_INTENT)!!}")
            Log.e("intent 2","email == ${intent.getStringExtra(Consts.USER_EMAIL_INTENT)!!}")
            viewModel.register(User(null
                ,intent.getStringExtra(Consts.USER_NAME_INTENT)!!
                ,intent.getStringExtra(Consts.USER_LASTNAME_INTENT)!!,
                intent.getStringExtra(Consts.USER_EMAIL_INTENT)!!,
                intent.getStringExtra(Consts.USER_PASSWORD_INTENT)!!,
                selectedRole,
                viewModel.selectedFields.value!!.toList(),
                "",
                false,
                "",
                listOf()
            ))
        }
        viewModel.canPass.observe(this, Observer {
            Log.e("response","account is $it")
            startActivity(Intent(this,LoginView::class.java))
        })
    }
    private fun chooseRole(cardOn:CardView,cardOff:CardView){
        cardOn.setBackgroundColor(resources.getColor(R.color.selectedCard))
        cardOff.setBackgroundColor(resources.getColor(R.color.white))
    }
}