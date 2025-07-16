package com.wevx.dealership

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.wevx.dealership.databinding.ActivityMainBinding
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private lateinit var backPressCallback: OnBackPressedCallback
    private val url = "https://tst.deepsoftwaresolution.com/home"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            webView.loadUrl(url)
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
        }


        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    binding.webView.canGoBack() -> binding.webView.goBack()
                    binding.webView.url == url -> handleDoubleBackToExit()
                    else -> finish() // If not on home but no history, just exit
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, backPressCallback)
    }

    private fun handleDoubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            finish()
        } else {
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                doubleBackToExitPressedOnce = false
            }, 2000)
        }
    }
}