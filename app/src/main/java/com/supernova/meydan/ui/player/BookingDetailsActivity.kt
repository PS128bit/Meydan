package com.supernova.meydan.ui.player

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.supernova.meydan.R
import com.supernova.meydan.demo.BookingStatus
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.ui.EXTRA_BOOKING
import com.supernova.meydan.ui.bindTimelineStep
import com.supernova.meydan.ui.colorOf
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.rupees
import com.supernova.meydan.ui.showStatus
import com.supernova.meydan.ui.toast

class BookingDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        val booking = DemoData.bookings[intent.getIntExtra(EXTRA_BOOKING, 0)]
        val venue = DemoData.venues[booking.venueIndex]
        val confirmed = booking.status == BookingStatus.CONFIRMED || booking.status == BookingStatus.PLAYED

        // Status card (dark)
        findViewById<TextView>(R.id.txtBookingStatus).apply {
            showStatus(booking.status)
            if (booking.status == BookingStatus.CONFIRMED) {
                backgroundTintList = android.content.res.ColorStateList.valueOf(colorOf(R.color.mint))
                setTextColor(colorOf(R.color.obsidian))
            }
        }
        findViewById<TextView>(R.id.txtBookingDate).text = booking.fullDate
        findViewById<TextView>(R.id.txtBookingTime).text = booking.time
        findViewById<TextView>(R.id.txtBookingVenue).text = venue.name
        findViewById<TextView>(R.id.txtBookingCourt).text = "${booking.court} · 10 players · Booking #MD-2318"

        // Timeline
        bindTimelineStep(findViewById(R.id.stepRequested), "Request sent", "Thu 21 Nov, 6:12 PM", done = true, last = false)
        bindTimelineStep(
            findViewById(R.id.stepAccepted),
            if (confirmed) "${venue.owner} accepted" else "Waiting for ${venue.owner}",
            if (confirmed) "Thu 21 Nov, 6:25 PM" else "Usually within 15 min",
            done = confirmed, last = false,
        )
        bindTimelineStep(
            findViewById(R.id.stepMatchDay), "Match day", "${booking.fullDate}, ${booking.time}",
            done = booking.status == BookingStatus.PLAYED, last = false,
        )
        val stepRate = findViewById<View>(R.id.stepRate)
        bindTimelineStep(stepRate, "Rate your game", "Tap here after you play", done = false, last = true)
        stepRate.setOnClickListener { navigate(RateGameActivity::class.java) }

        // Owner contact: number only shown once the owner confirms
        findViewById<TextView>(R.id.txtOwnerInitials).text = venue.ownerInitials
        findViewById<TextView>(R.id.txtOwnerName).text = venue.owner
        findViewById<TextView>(R.id.txtOwnerPhone).text =
            if (confirmed) DemoData.OWNER_PHONE else "Shared once confirmed"
        val btnCall = findViewById<View>(R.id.btnCallOwner)
        btnCall.visibility = if (confirmed) View.VISIBLE else View.GONE
        btnCall.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + DemoData.OWNER_PHONE.replace(" ", ""))))
        }
        findViewById<View>(R.id.btnMessageOwner).setOnClickListener { toast("Chat with the owner comes later") }

        // Location
        findViewById<TextView>(R.id.txtBookingAddress).text = venue.address
        findViewById<View>(R.id.btnDirections).setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Uri.encode(venue.address))))
            } catch (e: ActivityNotFoundException) {
                toast("No maps app on this phone")
            }
        }

        // Payment
        findViewById<TextView>(R.id.txtBookingRate).text =
            "${booking.hours} ${if (booking.hours == 1) "hour" else "hours"} × ${rupees(venue.pricePerHour)}"
        findViewById<TextView>(R.id.txtBookingTotal).text = rupees(venue.pricePerHour * booking.hours)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnMore).setOnClickListener { toast("More options come later") }
        findViewById<View>(R.id.btnCancelBooking).setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Cancel this booking?")
                .setMessage("${venue.owner} will be notified and the slot opens up for other players.")
                .setNegativeButton("Keep it", null)
                .setPositiveButton("Cancel booking") { _, _ ->
                    toast("Booking cancelled")
                    finish()
                }
                .show()
        }
    }
}
