package com.example.najahni.views.cart.webview

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.najahni.R
import com.example.najahni.roomDB.CartViewModel
import com.example.najahni.utils.SharedPrefsNajahni
import com.example.najahni.views.courseDetail.CourseDetailViewModel

class PayementWebViewActivity : AppCompatActivity() {
    private lateinit var webView : WebView
    private lateinit var viewModel : CourseDetailViewModel
    private lateinit var cartViewModel : CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payement_web_view)
        viewModel = ViewModelProvider(this)[CourseDetailViewModel::class.java]
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        webView = findViewById<WebView>(R.id.webView)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val currentUrl = request?.url.toString()
                Log.e("WebView",request?.url.toString())
                val courseId = intent.getStringExtra("courseID").toString()
                if(currentUrl.contains("merchants")){
                    val sharedPreferences: SharedPreferences =
                        getSharedPreferences(SharedPrefsNajahni.SHARED_PREFS, Context.MODE_PRIVATE)
                    val token = SharedPrefsNajahni.getToken(sharedPreferences)
                    viewModel.enrollInCourse(token,courseId)
                }
                viewModel.enrollSucess.observe(this@PayementWebViewActivity) {
                    if (it) {
                        cartViewModel.deleteById(this@PayementWebViewActivity,courseId)
                        finish()
                    }else{
                        Toast.makeText(this@PayementWebViewActivity, viewModel.message.value, Toast.LENGTH_LONG).show()

                    }
                }
                return false
            }
        }
        val urlLoad = intent.getStringExtra("url").toString()
        //webView.loadUrl("https://preprod.konnect.network/gateway/6394711108ec811bcda332cb/wallets")
        webView.loadUrl(urlLoad)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
    }
}