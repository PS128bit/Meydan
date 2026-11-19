package com.supernova.meydan.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.supernova.meydan.R
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.player.HomeActivity
import com.supernova.meydan.ui.startFresh
import com.supernova.meydan.ui.toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Demo: any input logs in. Real phone/email + password check comes with the backend.
        findViewById<View>(R.id.btnLogin).setOnClickListener { startFresh(HomeActivity::class.java) }
        findViewById<View>(R.id.btnGoToSignup).setOnClickListener { navigate(SignupActivity::class.java) }
        findViewById<View>(R.id.txtForgot).setOnClickListener {
            toast("Password reset comes with the backend")
        }
    }
}
