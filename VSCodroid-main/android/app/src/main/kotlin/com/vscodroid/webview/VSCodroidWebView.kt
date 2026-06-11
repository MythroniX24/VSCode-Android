package com.vscodroid.webview

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import com.vscodroid.util.Logger

object VSCodroidWebView {
    private const val TAG = "WebView"

    @SuppressLint("SetJavaScriptEnabled")
    fun configure(webView: WebView) {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            @Suppress("DEPRECATION")
            databaseEnabled = true
            
            // Force desktop mode by spoofing user agent
            val baseAgent = userAgentString
            userAgentString = baseAgent.replace("Mobile", "")
                .replace(Regex("Android [0-9.]+;"), "X11; Linux x86_64;")
                
            // Configure viewport for desktop rendering
            useWideViewPort = true
            loadWithOverviewMode = true
            
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            cacheMode = WebSettings.LOAD_DEFAULT
            allowContentAccess = true
            allowFileAccess = false
            mediaPlaybackRequiresUserGesture = false
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(false)
        }

        webView.isScrollbarFadingEnabled = true
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER

        if (Logger.debugEnabled) {
            WebView.setWebContentsDebuggingEnabled(true)
            Logger.d(TAG, "WebView remote debugging enabled")
        }

        Logger.i(TAG, "WebView configured")
    }
}
