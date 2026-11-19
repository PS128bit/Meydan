package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.supernova.meydan.R
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.ui.EXTRA_DAY
import com.supernova.meydan.ui.EXTRA_HOURS
import com.supernova.meydan.ui.EXTRA_SLOTS
import com.supernova.meydan.ui.EXTRA_VENUE
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.rupees

class SendRequestActivity : AppCompatActivity() {

    private var players = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_request)

        val venueIndex = intent.getIntExtra(EXTRA_VENUE, 0)
        val venue = DemoData.venues[venueIndex]
        val hours = intent.getIntExtra(EXTRA_HOURS, 2)
        val slots = intent.getStringExtra(EXTRA_SLOTS) ?: "8 PM, 9 PM"
        val day = intent.getStringExtra(EXTRA_DAY) ?: "Sat, 23 Nov"

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<TextView>(R.id.txtReqVenue).text = venue.name
        findViewById<TextView>(R.id.txtReqCourt).text = "${venue.sport} · Court 2 · ${venue.area}"
        findViewById<TextView>(R.id.txtReqDate).text = day
        findViewById<TextView>(R.id.txtReqTime).text = slots
        findViewById<TextView>(R.id.txtReqRate).text = "$hours × ${rupees(venue.pricePerHour)}"
        findViewById<TextView>(R.id.txtReqTotal).text = rupees(venue.pricePerHour * hours)

        val txtPlayers = findViewById<TextView>(R.id.txtPlayers)
        txtPlayers.text = players.toString()
        findViewById<View>(R.id.btnPlayersMinus).setOnClickListener {
            if (players > 2) players--
            txtPlayers.text = players.toString()
        }
        findViewById<View>(R.id.btnPlayersPlus).setOnClickListener {
            if (players < 22) players++
            txtPlayers.text = players.toString()
        }

        findViewById<View>(R.id.btnSendRequest).setOnClickListener {
            navigate(RequestSentActivity::class.java) { putExtra(EXTRA_VENUE, venueIndex) }
            finish()
        }
    }
}
