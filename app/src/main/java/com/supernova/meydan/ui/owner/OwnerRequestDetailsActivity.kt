package com.supernova.meydan.ui.owner

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.supernova.meydan.R
import com.supernova.meydan.demo.OwnerDemoData
import com.supernova.meydan.demo.RequestStatus
import com.supernova.meydan.ui.EXTRA_REQUEST
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.toast

class OwnerRequestDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_request_details)

        val req = OwnerDemoData.requests[intent.getIntExtra(EXTRA_REQUEST, 0)]
        val hours = if (req.hours == 1) "1 hour" else "${req.hours} hours"

        findViewById<TextView>(R.id.txtPlayerInitials).text = req.initials
        findViewById<TextView>(R.id.txtPlayerName).text = req.name
        findViewById<TextView>(R.id.txtPlayerMeta).text = req.playerMeta
        findViewById<TextView>(R.id.txtNumberNote).text = "${req.firstName}'s number is shared once you accept."
        findViewById<TextView>(R.id.txtSlotDay).text = "${req.date} · $hours"
        findViewById<TextView>(R.id.txtSlotTime).text = req.time
        findViewById<TextView>(R.id.txtSlotPrice).text = req.price
        findViewById<TextView>(R.id.txtMessageLabel).text = "${req.firstName} says"
        findViewById<TextView>(R.id.txtMessage).text = req.message
        findViewById<TextView>(R.id.txtHeadsUp).text = "Accepting blocks ${req.shortTime} on ${req.date}."

        // Already answered: show the result instead of the buttons
        val btnAccept = findViewById<View>(R.id.btnAccept)
        val btnDecline = findViewById<View>(R.id.btnDecline)
        if (req.status != RequestStatus.PENDING) {
            btnAccept.isEnabled = false
            btnDecline.visibility = View.GONE
            (btnAccept as TextView).text = if (req.status == RequestStatus.ACCEPTED) "Accepted" else "Declined"
        }

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        btnAccept.setOnClickListener {
            req.status = RequestStatus.ACCEPTED
            toast("Accepted. ${req.firstName} gets a notification.")
            navigate(OwnerScheduleActivity::class.java)
            finish()
        }
        btnDecline.setOnClickListener {
            req.status = RequestStatus.DECLINED
            toast("Declined. ${req.firstName} gets a notification.")
            finish()
        }
    }
}
