package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.supernova.meydan.R
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.demo.ReviewAdapter
import com.supernova.meydan.ui.EXTRA_VENUE
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.rupees
import com.supernova.meydan.ui.tintImage
import com.supernova.meydan.ui.toast

class VenueDetailsActivity : AppCompatActivity() {

    private var saved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_details)

        val index = intent.getIntExtra(EXTRA_VENUE, 0)
        val venue = DemoData.venues[index]

        findViewById<ImageView>(R.id.imgCover).setImageResource(venue.court)
        findViewById<TextView>(R.id.txtPhotoCount).text = "1 / 6"
        findViewById<TextView>(R.id.txtSportType).text = venue.sportType
        findViewById<TextView>(R.id.txtVenueName).text = venue.name
        findViewById<TextView>(R.id.txtAddress).text = "${venue.address} · ${venue.distance}"
        findViewById<TextView>(R.id.txtRating).text = venue.rating
        findViewById<TextView>(R.id.txtReviewCount).text = "${venue.reviews} reviews · Replies in ~15 min"
        findViewById<TextView>(R.id.txtFactSize).text = venue.size
        findViewById<TextView>(R.id.txtFactSurface).text = venue.surface
        findViewById<TextView>(R.id.txtFactCourts).text = venue.courts.toString()
        findViewById<TextView>(R.id.txtAbout).text = venue.about
        findViewById<TextView>(R.id.txtHours).text = "6:00 AM – 11:00 PM, every day"
        findViewById<TextView>(R.id.txtOwnerInitials).text = venue.ownerInitials
        findViewById<TextView>(R.id.txtOwnerName).text = venue.owner
        findViewById<TextView>(R.id.txtOwnerMeta).text = "★ 4.9 owner rating · Replies fast"
        findViewById<TextView>(R.id.txtPrice).text = rupees(venue.pricePerHour)
        findViewById<RecyclerView>(R.id.rvReviews).adapter = ReviewAdapter(DemoData.reviews.take(2))

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnShare).setOnClickListener { toast("Sharing comes in the next phase") }
        val btnSave = findViewById<ImageButton>(R.id.btnSave)
        btnSave.setOnClickListener {
            saved = !saved
            btnSave.setImageResource(if (saved) R.drawable.ic_heart_filled else R.drawable.ic_heart)
            btnSave.tintImage(if (saved) R.color.red else R.color.obsidian)
            toast(if (saved) "Saved" else "Removed from saved")
        }
        findViewById<View>(R.id.txtSeeAllReviews).setOnClickListener { toast("All ${venue.reviews} reviews") }
        findViewById<View>(R.id.btnSeeSlots).setOnClickListener {
            navigate(PickSlotActivity::class.java) { putExtra(EXTRA_VENUE, index) }
        }
    }
}
