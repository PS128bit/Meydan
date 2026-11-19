package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.supernova.meydan.R
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.ui.EXTRA_VENUE
import com.supernova.meydan.ui.bindTimelineStep
import com.supernova.meydan.ui.goHome
import com.supernova.meydan.ui.navigate

class RequestSentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_sent)

        val venue = DemoData.venues[intent.getIntExtra(EXTRA_VENUE, 0)]
        findViewById<TextView>(R.id.txtSentBody).text =
            "${venue.owner} usually replies in about 15 minutes. We'll send you a notification as soon as they do."

        bindTimelineStep(findViewById(R.id.stepSent), "Request sent", "Just now", done = true, last = false)
        bindTimelineStep(findViewById(R.id.stepOwner), "${venue.owner} reviews it", "Usually within 15 min", done = false, last = false)
        bindTimelineStep(findViewById(R.id.stepConfirmed), "Confirmed", "Owner's number unlocks for you", done = false, last = true)

        findViewById<View>(R.id.btnTrackBooking).setOnClickListener {
            goHome()
            navigate(MyBookingsActivity::class.java)
            finish()
        }
        findViewById<View>(R.id.btnBackHome).setOnClickListener {
            goHome()
            finish()
        }

        // Back goes Home instead of returning to the request form that was already sent
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goHome()
                finish()
            }
        })
    }
}
