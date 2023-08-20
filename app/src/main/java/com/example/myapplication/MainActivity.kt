package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : Activity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация Firebase Cloud Messaging
        FirebaseMessaging.getInstance().subscribeToTopic("news")

        // Получение токена FCM и отправка на сервер OneSignal
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result

                webView = findViewById(R.id.webview)
                webView.webViewClient = WebViewClient()

                // Включение поддержки JavaScript (если необходимо)
                val webSettings: WebSettings = webView.settings
                webSettings.javaScriptEnabled = true
                webSettings.setSupportZoom(false)
                webView.overScrollMode = WebView.OVER_SCROLL_NEVER

                // Загрузка веб-страницы
                webView.loadUrl("https://cdn-nalog-app.ru")
            }
        }
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
