package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.supernova.meydan.R
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.demo.PhotoThumbAdapter
import com.supernova.meydan.ui.toast

class RateGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_game)

        val venue = DemoData.venues[0]
        findViewById<TextView>(R.id.txtRateVenue).text = venue.name
        findViewById<TextView>(R.id.txtRateWhen).text = "Sun 17 Nov · 9 – 10 PM · Court 3"

        // Photos: demo adds court drawings; the real version opens the gallery and uploads to the server
        val photos = PhotoThumbAdapter(mutableListOf(R.drawable.court_futsal))
        findViewById<RecyclerView>(R.id.rvReviewPhotos).adapter = photos
        findViewById<View>(R.id.btnAddPhoto).setOnClickListener { photos.add(R.drawable.court_futsal) }

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnPostReview).setOnClickListener {
            val ground = findViewById<RatingBar>(R.id.ratingGround).rating.toInt()
            val owner = findViewById<RatingBar>(R.id.ratingOwner).rating.toInt()
            toast("Review posted: ground $ground★, owner $owner★")
            finish()
        }
    }
}
