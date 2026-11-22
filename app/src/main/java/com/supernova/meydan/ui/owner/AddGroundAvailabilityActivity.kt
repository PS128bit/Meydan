package com.supernova.meydan.ui.owner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.supernova.meydan.R
import com.supernova.meydan.demo.OwnerDemoData
import com.supernova.meydan.ui.toast

class AddGroundAvailabilityActivity : AppCompatActivity() {

    private val dayIds = listOf(R.id.dayMon, R.id.dayTue, R.id.dayWed, R.id.dayThu, R.id.dayFri, R.id.daySat, R.id.daySun)
    private val dayNames = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    private lateinit var slotLength: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_add_ground_availability)

        findViewById<TextView>(R.id.txtHoursRange).text = "4:00 PM – 3:00 AM"
        findViewById<TextView>(R.id.txtBlockedDate).text = "Fri 20 Nov · Private event"

        slotLength = findViewById(R.id.segSlotLength)
        slotLength.setOnCheckedChangeListener { _, _ -> updatePreview() }
        dayIds.forEach { id -> findViewById<CheckBox>(id).setOnCheckedChangeListener { _, _ -> updatePreview() } }
        updatePreview()

        findViewById<View>(R.id.btnRemoveBlockedDate).setOnClickListener {
            findViewById<View>(R.id.rowBlockedDate).visibility = View.GONE
        }
        findViewById<View>(R.id.btnAddBlockedDate).setOnClickListener { toast("Date picker comes in the next phase") }
        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnPublish).setOnClickListener {
            toast("${OwnerDemoData.GROUND} is live")
            startActivity(
                Intent(this, OwnerHomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            )
            finish()
        }
    }

    /** "Players will see": slots a day (11 open hours / slot length) and which days. */
    private fun updatePreview() {
        val openHours = 11 // 4 PM to 3 AM
        val perDay = when (slotLength.checkedRadioButtonId) {
            R.id.segHalfHour -> openHours * 2
            R.id.segTwoHours -> openHours / 2
            else -> openHours
        }
        val on = dayIds.indices.filter { findViewById<CheckBox>(dayIds[it]).isChecked }
        val days = when {
            on.isEmpty() -> "no days picked"
            on.size == 7 -> "every day"
            on.last() - on.first() == on.size - 1 && on.size > 1 -> "${dayNames[on.first()]} – ${dayNames[on.last()]}"
            else -> on.joinToString(", ") { dayNames[it] }
        }
        findViewById<TextView>(R.id.txtSlotsPerDay).text = if (on.isEmpty()) "0" else perDay.toString()
        findViewById<TextView>(R.id.txtSlotsPerDayLabel).text = "slots a day, $days"
    }
}
