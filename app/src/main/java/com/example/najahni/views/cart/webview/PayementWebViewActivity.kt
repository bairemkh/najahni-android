package com.example.najahni.views.cart.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.najahni.R

class PayementWebViewActivity : AppCompatActivity() {
    private lateinit var webView : WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payement_web_view)

        webView = findViewById<WebView>(R.id.webView)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val currentUrl = request?.url.toString()
                Log.e("WebView",request?.url.toString())
                if(currentUrl.contains("merchants")){
                    finish()
                }
                return false
            }
        }

        webView.loadUrl("https://preprod.konnect.network/gateway/6394711108ec811bcda332cb/wallets")

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
    }
}