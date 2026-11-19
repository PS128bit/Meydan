package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.supernova.meydan.R
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.demo.SportAdapter
import com.supernova.meydan.demo.TicketAdapter
import com.supernova.meydan.demo.VenueAdapter
import com.supernova.meydan.ui.EXTRA_BOOKING
import com.supernova.meydan.ui.EXTRA_SPORT
import com.supernova.meydan.ui.EXTRA_VENUE
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.setupPlayerNav
import java.util.Calendar

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupPlayerNav(R.id.nav_home)

        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val partOfDay = when (hour) {
            in 5..11 -> "Morning"
            in 12..16 -> "Afternoon"
            else -> "Evening"
        }
        findViewById<TextView>(R.id.txtGreeting).text = "$partOfDay, ${DemoData.USER_FIRST_NAME}"
        findViewById<TextView>(R.id.txtLocation).text = DemoData.LOCATION

        findViewById<View>(R.id.btnSearch).setOnClickListener { navigate(SearchResultsActivity::class.java) }
        findViewById<View>(R.id.btnFilters).setOnClickListener { navigate(FiltersActivity::class.java) }
        findViewById<View>(R.id.btnNotifications).setOnClickListener { navigate(NotificationsActivity::class.java) }
        findViewById<View>(R.id.txtAllBookings).setOnClickListener { navigate(MyBookingsActivity::class.java) }
        findViewById<View>(R.id.txtSeeAllNear).setOnClickListener { navigate(SearchResultsActivity::class.java) }

        findViewById<RecyclerView>(R.id.rvSports).adapter = SportAdapter(DemoData.sports) { sport ->
            navigate(SearchResultsActivity::class.java) { putExtra(EXTRA_SPORT, sport.name) }
        }

        // "Your next match" ticket
        val nextMatch = findViewById<View>(R.id.nextMatch)
        TicketAdapter.bindTicket(nextMatch, DemoData.bookings[0])
        nextMatch.findViewById<View>(R.id.cardTicket).setOnClickListener {
            navigate(BookingDetailsActivity::class.java) { putExtra(EXTRA_BOOKING, 0) }
        }

        findViewById<RecyclerView>(R.id.rvNearby).adapter =
            VenueAdapter(DemoData.venues.take(3)) { index ->
                navigate(VenueDetailsActivity::class.java) { putExtra(EXTRA_VENUE, index) }
            }
    }
}
