package com.supernova.meydan.ui.owner

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
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

class OwnerRequestsActivity : AppCompatActivity() {

    private lateinit var adapter: OwnerRequestAdapter
    private lateinit var tabs: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_requests)
        setupOwnerNav(R.id.nav_owner_requests)

        adapter = OwnerRequestAdapter(
            emptyList(), compact = false,
            onOpen = { index -> navigate(OwnerRequestDetailsActivity::class.java) { putExtra(EXTRA_REQUEST, index) } },
            onAccept = { index -> answer(index, RequestStatus.ACCEPTED) },
            onDecline = { index -> answer(index, RequestStatus.DECLINED) },
        )
        findViewById<RecyclerView>(R.id.rvOwnerRequests).adapter = adapter

        tabs = findViewById(R.id.segRequestStatus)
        tabs.setOnCheckedChangeListener { _, _ -> refresh() }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun answer(index: Int, status: RequestStatus) {
        val req = OwnerDemoData.requests[index]
        req.status = status
        toast(if (status == RequestStatus.ACCEPTED) "Accepted. ${req.firstName} gets a notification." else "Declined")
        refresh()
    }

    private fun refresh() {
        val status = when (tabs.checkedRadioButtonId) {
            R.id.segAccepted -> RequestStatus.ACCEPTED
            R.id.segDeclined -> RequestStatus.DECLINED
            else -> RequestStatus.PENDING
        }
        val list = OwnerDemoData.requests.filter { it.status == status }
        adapter.submit(list)
        findViewById<View>(R.id.txtRequestsEmpty).visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        val newCount = OwnerDemoData.pending().size
        findViewById<TextView>(R.id.txtNewCount).apply {
            text = "$newCount new"
            visibility = if (newCount == 0) View.GONE else View.VISIBLE
        }
    }
}
