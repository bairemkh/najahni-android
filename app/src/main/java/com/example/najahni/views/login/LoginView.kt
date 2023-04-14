package com.example.najahni.views.login

import android.app.Application
import android.content.Intent
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.bottombarnavigation.MainActivity
import com.example.najahni.models.CurrentUser
import com.example.najahni.models.User
import com.example.najahni.services.implementation.UserService
import com.example.najahni.utils.ApiResponseHandling
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.utils.SharedPrefsNajahni.SHARED_PREFS
import com.example.najahni.views.signup.SignUpActivity
import com.example.najahni.views.signup.SignupPage2
import com.example.najahni.views.forgetpassword.ForgetPasswordActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText


class LoginView : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var email: String
    lateinit var firstname: String
    lateinit var lastname: String
    lateinit var picUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestId()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(applicationContext, gso)

        findViewById<TextView>(R.id.forgetpwd).setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            it.isClickable = false
            viewModel.onLoginClicked(
                findViewById<EditText>(R.id.loginEmail).text.toString(),
                findViewById<EditText>(R.id.loginPassword).text.toString()
            )
        }
        findViewById<TextView>(R.id.toSignUp).setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        viewModel.loginSuccess.observe(this, Observer {
            if (!it) {
                findViewById<Button>(R.id.loginBtn).isClickable = true;
                Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
            } else {
                findViewById<Button>(R.id.loginBtn).isClickable = true
                Log.e("token", viewModel.token.value.orEmpty())
                val sharedPreferences: SharedPreferences =
                    getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                SharedPrefsNajahni.setToken(sharedPreferences, viewModel.token.value.orEmpty())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        findViewById<Button>(R.id.google_login_btn).setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, 10001
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10001) {
            if(resultCode == RESULT_OK){
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }

        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            Log.e("acccc",account.toString())

            firstname = account?.givenName ?: ""

            lastname = account?.familyName ?: ""

            email = account?.email ?: ""

            picUrl = account?.photoUrl.toString()

            viewModel.onGoogleSignin(email," ",firstname,lastname,picUrl)
            viewModel.loginSuccess.observe(this, Observer {
                if (!it) {
                    Toast.makeText(this, viewModel.message.value, Toast.LENGTH_LONG).show()
                } else {
                    Log.e("token", viewModel.token.value.orEmpty())
                    val sharedPreferences: SharedPreferences =
                        getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                    SharedPrefsNajahni.setToken(sharedPreferences, viewModel.token.value.orEmpty())
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)

                }
            })

        } catch (e: ApiException) {
            Log.e(
                "failed code=", e.toString()
            )
        }
    }

}