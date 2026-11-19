package com.supernova.meydan.ui.player

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.supernova.meydan.R
import com.supernova.meydan.demo.DayAdapter
import com.supernova.meydan.demo.DemoData
import com.supernova.meydan.ui.EXTRA_DAY
import com.supernova.meydan.ui.EXTRA_HOURS
import com.supernova.meydan.ui.EXTRA_SLOTS
import com.supernova.meydan.ui.EXTRA_VENUE
import com.supernova.meydan.ui.colorOf
import com.supernova.meydan.ui.navigate
import com.supernova.meydan.ui.rupees
import com.supernova.meydan.ui.toast

class PickSlotActivity : AppCompatActivity() {

    private enum class SlotState { OPEN, PICKED, TAKEN }

    // Same order as the slots in activity_pick_slot.xml (4 PM ... 12 AM)
    private val states = mutableListOf(
        SlotState.TAKEN, SlotState.OPEN, SlotState.OPEN,
        SlotState.TAKEN, SlotState.PICKED, SlotState.PICKED,
        SlotState.OPEN, SlotState.TAKEN, SlotState.TAKEN,
    )

    private lateinit var grid: GridLayout
    private lateinit var txtSelectedTime: TextView
    private lateinit var txtSelectedTotal: TextView
    private lateinit var btnNext: MaterialButton
    private var venueIndex = 0
    private var dayLabel = "Sat, 23 Nov"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_slot)

        venueIndex = intent.getIntExtra(EXTRA_VENUE, 0)
        findViewById<TextView>(R.id.txtSlotVenue).text = DemoData.venues[venueIndex].name

        grid = findViewById(R.id.gridSlots)
        txtSelectedTime = findViewById(R.id.txtSelectedTime)
        txtSelectedTotal = findViewById(R.id.txtSelectedTotal)
        btnNext = findViewById(R.id.btnNext)

        findViewById<RecyclerView>(R.id.rvDays).adapter = DayAdapter(DemoData.days, 2) { day ->
            dayLabel = "${day.name.lowercase().replaceFirstChar { it.uppercase() }}, ${day.number} Nov"
        }

        for (i in 0 until grid.childCount) {
            val slot = grid.getChildAt(i)
            slot.setOnClickListener {
                when (states[i]) {
                    SlotState.OPEN -> states[i] = SlotState.PICKED
                    SlotState.PICKED -> states[i] = SlotState.OPEN
                    SlotState.TAKEN -> toast("That hour is already booked")
                }
                render()
            }
        }
        render()

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        btnNext.setOnClickListener {
            navigate(SendRequestActivity::class.java) {
                putExtra(EXTRA_VENUE, venueIndex)
                putExtra(EXTRA_HOURS, pickedLabels().size)
                putExtra(EXTRA_SLOTS, pickedLabels().joinToString(", "))
                putExtra(EXTRA_DAY, dayLabel)
            }
        }
    }

    private fun pickedLabels(): List<String> =
        (0 until grid.childCount)
            .filter { states[it] == SlotState.PICKED }
            .map { (grid.getChildAt(it) as TextView).text.toString() }

    private fun render() {
        for (i in 0 until grid.childCount) {
            val slot = grid.getChildAt(i) as TextView
            when (states[i]) {
                SlotState.OPEN -> {
                    slot.setBackgroundResource(R.drawable.bg_slot_open)
                    slot.setTextColor(colorOf(R.color.white))
                }
                SlotState.PICKED -> {
                    slot.setBackgroundResource(R.drawable.bg_slot_selected)
                    slot.setTextColor(colorOf(R.color.obsidian))
                }
                SlotState.TAKEN -> {
                    slot.setBackgroundResource(R.drawable.bg_slot_taken)
                    slot.setTextColor(colorOf(R.color.line_dark))
                }
            }
        }
        val picked = pickedLabels()
        val hours = picked.size
        txtSelectedTime.text = when (hours) {
            0 -> "Pick at least one hour"
            1 -> "1 hour · ${picked[0]}"
            else -> "$hours hours · ${picked.joinToString(", ")}"
        }
        txtSelectedTotal.text = rupees(DemoData.venues[venueIndex].pricePerHour * hours)
        btnNext.isEnabled = hours > 0
    }
}
