package com.supernova.meydan.ui.owner

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.supernova.meydan.R
import com.supernova.meydan.demo.DayAdapter
import com.supernova.meydan.demo.OwnerDemoData
import com.supernova.meydan.demo.ScheduleAdapter
import com.supernova.meydan.demo.SlotKind
import com.supernova.meydan.ui.EXTRA_REQUEST
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.setupOwnerNav
import com.supernova.meydan.ui.toast

class OwnerScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_schedule)
        setupOwnerNav(R.id.nav_owner_schedule)

        findViewById<TextView>(R.id.txtScheduleGround).text = OwnerDemoData.GROUND
        findViewById<View>(R.id.btnGroundSwitcher).setOnClickListener { toast("You have one ground for now") }

        findViewById<RecyclerView>(R.id.rvScheduleDays).adapter =
            DayAdapter(OwnerDemoData.scheduleDays, 1, light = true) { day ->
                toast("${day.name.lowercase().replaceFirstChar { it.uppercase() }} ${day.number} Nov")
            }

        val slots = OwnerDemoData.fridaySchedule
        findViewById<TextView>(R.id.txtCountBooked).text = "${slots.count { it.kind == SlotKind.BOOKED }} booked"
        findViewById<TextView>(R.id.txtCountPending).text = "${slots.count { it.kind == SlotKind.PENDING }} pending"
        findViewById<TextView>(R.id.txtCountOpen).text = "${slots.count { it.kind == SlotKind.OPEN }} open"

        findViewById<RecyclerView>(R.id.rvScheduleSlots).adapter = ScheduleAdapter(
            slots,
            onPendingClick = { slot ->
                navigate(OwnerRequestDetailsActivity::class.java) { putExtra(EXTRA_REQUEST, slot.requestIndex) }
            },
            onBlockClick = { slot -> toast("${slot.time} blocked. Players will see it as taken.") },
        )
    }
}
