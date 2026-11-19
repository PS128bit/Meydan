package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch
import com.supernova.meydan.R
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.ui.auth.LoginActivity
import com.supernova.meydan.ui.setupPlayerNav
import com.supernova.meydan.ui.startFresh
import com.supernova.meydan.ui.toast

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupPlayerNav(R.id.nav_profile)

        findViewById<TextView>(R.id.txtProfileInitials).text = DemoData.USER_INITIALS
        findViewById<TextView>(R.id.txtProfileName).text = DemoData.USER_NAME
        findViewById<TextView>(R.id.txtProfilePhone).text = DemoData.USER_PHONE
        findViewById<TextView>(R.id.txtProfileSince).text = "Playing since March 2025"
        findViewById<TextView>(R.id.txtStatGames).text = "14"
        findViewById<TextView>(R.id.txtStatGrounds).text = "6"
        findViewById<TextView>(R.id.txtStatRating).text = "4.9"
        findViewById<TextView>(R.id.txtVersion).text = "Meydan 0.1 · Team Supernova"

        // Demo user signed up without an email, so show the "Add your email" card
        val cardAddEmail = findViewById<View>(R.id.cardAddEmail)
        cardAddEmail.visibility = View.VISIBLE
        findViewById<View>(R.id.btnAddEmail).setOnClickListener {
            toast("Email added")
            cardAddEmail.visibility = View.GONE
        }

        val txtLastSynced = findViewById<TextView>(R.id.txtLastSynced)
        txtLastSynced.text = "Last synced 2 min ago · 1 item waiting"
        findViewById<View>(R.id.btnSyncNow).setOnClickListener {
            txtLastSynced.text = "Last synced just now · Everything is up to date"
            toast("Synced")
        }

        findViewById<MaterialSwitch>(R.id.switchPush).setOnCheckedChangeListener { _, on ->
            toast(if (on) "Notifications on" else "Notifications off")
        }

        val comingSoon = View.OnClickListener { toast("Coming in the next phase") }
        listOf(R.id.btnEditProfile, R.id.btnChangePhoto, R.id.rowPersonal, R.id.rowPhoneEmail,
            R.id.rowPassword, R.id.rowHelp, R.id.rowPrivacy).forEach { id ->
            findViewById<View>(id).setOnClickListener(comingSoon)
        }
        findViewById<View>(R.id.cardOwnerMode).setOnClickListener {
            toast("Owner mode is being built by Umair")
        }
        findViewById<View>(R.id.btnLogout).setOnClickListener { startFresh(LoginActivity::class.java) }
    }
}
