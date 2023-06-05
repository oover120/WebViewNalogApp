package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()

        // Включение поддержки JavaScript (если необходимо)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Загрузка веб-страницы
        webView.loadUrl("https://налоги-онлайн.рф/app/")
    }

    // Переопределение метода onBackPressed, чтобы при нажатии кнопки "Назад" возвратиться на предыдущую страницу
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
