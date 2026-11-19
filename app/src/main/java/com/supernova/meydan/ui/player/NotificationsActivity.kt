package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.supernova.meydan.R
import com.supernova.meydan.demo.AlertAction
import com.supernova.meydan.demo.AlertAdapter
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.ui.EXTRA_BOOKING
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.setupPlayerNav

class NotificationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        setupPlayerNav(R.id.nav_alerts)

        val onAction: (AlertAction) -> Unit = { action ->
            when (action) {
                AlertAction.TICKET -> navigate(BookingDetailsActivity::class.java) { putExtra(EXTRA_BOOKING, 0) }
                AlertAction.RATE -> navigate(RateGameActivity::class.java)
                AlertAction.NONE -> Unit
            }
        }
        val today = AlertAdapter(DemoData.alertsToday, onAction)
        val earlier = AlertAdapter(DemoData.alertsEarlier, onAction)
        findViewById<RecyclerView>(R.id.rvAlertsToday).adapter = today
        findViewById<RecyclerView>(R.id.rvAlertsEarlier).adapter = earlier

        findViewById<View>(R.id.txtMarkAllRead).setOnClickListener {
            today.markAllRead()
            earlier.markAllRead()
        }
    }
}
