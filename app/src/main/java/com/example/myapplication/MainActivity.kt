package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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
                webView.webViewClient = object : WebViewClient() {
                    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                        // Обработка ошибки загрузки страницы здесь
                        // Например, вы можете показать свое сообщение об ошибке или выполнить другие действия
                        val errorMessage = "Ошибка загрузки страницы: $description"
                        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                        // Если вы хотите просто игнорировать ошибку и не показывать ничего, то можно оставить пустую реализацию метода.
                    }

                    // Добавляем обработку ссылок с номерами телефонов
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        if (url != null && url.startsWith("tel:")) {
                            // Если ссылка начинается с "tel:", это номер телефона
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                            startActivity(intent)
                            return true
                        }
                        return false
                    }
                }

                // Включение поддержки JavaScript (если необходимо)
                val webSettings: WebSettings = webView.settings
                webSettings.javaScriptEnabled = true
                webSettings.setSupportZoom(false)
                webView.overScrollMode = WebView.OVER_SCROLL_NEVER

                // Удаление нижней границы и полос прокрутки
                webView.setVerticalScrollBarEnabled(false)
                webView.setHorizontalScrollBarEnabled(false)
                webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY)
                webView.setScrollbarFadingEnabled(true)

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
