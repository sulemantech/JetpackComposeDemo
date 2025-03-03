package com.example.composeproject.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.AndroidViewModel

class WebViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    val webView: WebView = WebView(application)

    private var webViewBundle: Bundle? = null

    init {
        webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
                builtInZoomControls = false
            }
            loadUrl("https://g8way-app.com/map/") // Load once
        }
    }

    fun saveState() {
        webViewBundle = Bundle().apply { webView.saveState(this) }
    }

    fun restoreState() {
        webViewBundle?.let { webView.restoreState(it) }
    }
}

