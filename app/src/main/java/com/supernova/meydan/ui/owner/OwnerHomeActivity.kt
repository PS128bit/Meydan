package com.supernova.meydan.ui.owner

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.supernova.meydan.R
import com.supernova.meydan.demo.OwnerDemoData
import com.supernova.meydan.demo.OwnerRequestAdapter
import com.supernova.meydan.demo.RequestStatus
import com.supernova.meydan.ui.EXTRA_REQUEST
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.setupOwnerNav
import com.supernova.meydan.ui.toast

class OwnerHomeActivity : AppCompatActivity() {

    private lateinit var adapter: OwnerRequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_home)
        setupOwnerNav(R.id.nav_owner_home)

        findViewById<TextView>(R.id.txtOwnerGreeting).text = "Good evening, ${OwnerDemoData.OWNER_FIRST_NAME}"
        findViewById<TextView>(R.id.txtSlotsBooked).text = "38"
        findViewById<TextView>(R.id.txtOccupancy).text = "${OwnerDemoData.GROUND} · 72% of hours filled"
        findViewById<TextView>(R.id.pillToday).text = "3 today"
        findViewById<TextView>(R.id.pillRating).text = "4.8 rating"

        adapter = OwnerRequestAdapter(
            emptyList(), compact = true,
            onOpen = { index -> navigate(OwnerRequestDetailsActivity::class.java) { putExtra(EXTRA_REQUEST, index) } },
            onAccept = { index -> answer(index, RequestStatus.ACCEPTED) },
            onDecline = { index -> answer(index, RequestStatus.DECLINED) },
        )
        findViewById<RecyclerView>(R.id.rvOwnerHomeRequests).adapter = adapter

        findViewById<View>(R.id.txtSeeAllRequests).setOnClickListener { navigate(OwnerRequestsActivity::class.java) }
        findViewById<View>(R.id.pillNewRequests).setOnClickListener { navigate(OwnerRequestsActivity::class.java) }
        findViewById<View>(R.id.btnOwnerNotifications).setOnClickListener { toast("Owner alerts come with push notifications") }
    }

    override fun onResume() {
        super.onResume()
        refresh() // requests may have been answered on another screen
    }

    private fun answer(index: Int, status: RequestStatus) {
        val req = OwnerDemoData.requests[index]
        req.status = status
        toast(if (status == RequestStatus.ACCEPTED) "Accepted. ${req.firstName} gets a notification." else "Declined")
        refresh()
    }

    private fun refresh() {
        val pending = OwnerDemoData.pending()
        adapter.submit(pending.take(2))
        findViewById<TextView>(R.id.pillNewRequests).text =
            if (pending.size == 1) "1 new request" else "${pending.size} new requests"
        findViewById<TextView>(R.id.txtSeeAllRequests).text = "See all ${pending.size}"
    }
}
