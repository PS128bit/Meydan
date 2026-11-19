package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.supernova.meydan.R
import com.supernova.meydan.demo.Booking
import com.supernova.meydan.demo.BookingStatus
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.demo.TicketAdapter
import com.supernova.meydan.ui.EXTRA_BOOKING
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.setupPlayerNav

class MyBookingsActivity : AppCompatActivity() {

    private lateinit var adapter: TicketAdapter
    private lateinit var banner: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)
        setupPlayerNav(R.id.nav_bookings)

        banner = findViewById(R.id.bannerPendingSync)
        findViewById<TextView>(R.id.txtPendingSync).text =
            "1 request is waiting to sync. It'll send when you're back online."

        adapter = TicketAdapter(emptyList()) { index ->
            navigate(BookingDetailsActivity::class.java) { putExtra(EXTRA_BOOKING, index) }
        }
        findViewById<RecyclerView>(R.id.rvBookings).adapter = adapter

        val tabs = findViewById<TabLayout>(R.id.tabsBookings)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) = showTab(tab.position)
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        showTab(0)
    }

    private fun showTab(position: Int) {
        val upcoming = setOf(BookingStatus.CONFIRMED, BookingStatus.PENDING, BookingStatus.WAITING_SYNC)
        val pending = setOf(BookingStatus.PENDING, BookingStatus.WAITING_SYNC)
        val list: List<Booking> = when (position) {
            0 -> DemoData.bookings.filter { it.status in upcoming }
            1 -> DemoData.bookings.filter { it.status in pending }
            else -> DemoData.bookings.filter { it.status !in upcoming }
        }
        adapter.submit(list)
        banner.visibility =
            if (list.any { it.status == BookingStatus.WAITING_SYNC }) View.VISIBLE else View.GONE
    }
}
