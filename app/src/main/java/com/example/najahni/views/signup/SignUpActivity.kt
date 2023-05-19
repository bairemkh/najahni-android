package com.example.najahni.views.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.utils.Consts
import com.example.najahni.views.login.LoginViewModel

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        /*findViewById<Button>(R.id.nextSignup).setOnClickListener {
            val intent = Intent(this,SignupPage2::class.java)
            startActivity(intent)
        }*/
        val viewModel = ViewModelProvider(this)[SignupViewModel::class.java]
        findViewById<Button>(R.id.nextSignup).setOnClickListener {
            viewModel.check(
                findViewById(R.id.nameSignup),findViewById<TextView>(R.id.lastNameSignup),findViewById<TextView>(R.id.emailSignup),
                findViewById(R.id.passwordSignup),findViewById(R.id.confirmPasswordSignup)
            )
        }
        viewModel.canPass.observe(this, Observer {
            if(it){
                val intent = Intent(this,SignupPage2::class.java)
                intent.putExtra(Consts.USER_NAME_INTENT,findViewById<TextView>(R.id.nameSignup).text.toString())
                intent.putExtra(Consts.USER_LASTNAME_INTENT,findViewById<TextView>(R.id.lastNameSignup).text.toString())
                intent.putExtra(Consts.USER_EMAIL_INTENT,findViewById<TextView>(R.id.emailSignup).text.toString())
                intent.putExtra(Consts.USER_PASSWORD_INTENT,findViewById<TextView>(R.id.passwordSignup).text.toString())
                Log.e("intent 1","password == ${findViewById<TextView>(R.id.passwordSignup).text.toString()}")
                Log.e("intent 1","email == ${findViewById<TextView>(R.id.emailSignup).text.toString()}")
                startActivity(intent)
            }
        })
    }
}