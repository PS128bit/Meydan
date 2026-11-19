package com.supernova.meydan.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.supernova.meydan.R

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private val goToLogin = Runnable {
        startActivity(Intent(this, LoginActivity::class.java))
        finish() // so Back from Log in doesn't return to the splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.postDelayed(goToLogin, SPLASH_DELAY_MS)
    }

    override fun onDestroy() {
        // If the user leaves during the splash, don't open Log in afterwards
        handler.removeCallbacks(goToLogin)
        super.onDestroy()
    }

    companion object {
        private const val SPLASH_DELAY_MS = 1500L
    }
}
