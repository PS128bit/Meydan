package com.supernova.meydan.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supernova.meydan.R
import com.supernova.meydan.ui.showStatus

/*
 * Adapters for the owner screens.
 */

/**
 * Booking request cards.
 * [compact] = the short card on Owner home (item_owner_request_compact),
 * otherwise the full card on Requests (item_owner_request).
 * Callbacks get the index into OwnerDemoData.requests.
 */
class OwnerRequestAdapter(
    private var items: List<OwnerRequest>,
    private val compact: Boolean,
    private val onOpen: (Int) -> Unit,
    private val onAccept: (Int) -> Unit,
    private val onDecline: (Int) -> Unit,
) : RecyclerView.Adapter<SimpleVH>() {

    fun submit(newItems: List<OwnerRequest>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleVH {
        val layout = if (compact) R.layout.item_owner_request_compact else R.layout.item_owner_request
        return SimpleVH(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        val req = items[position]
        val index = OwnerDemoData.requests.indexOf(req)
        val v = holder.itemView
        v.findViewById<TextView>(R.id.txtReqInitials).text = req.initials
        v.findViewById<TextView>(R.id.txtReqName).text = req.name
        v.findViewById<TextView>(R.id.txtReqPrice).text = req.price

        if (compact) {
            v.findViewById<TextView>(R.id.txtReqWhen).text = "${req.date} · ${req.shortTime}"
        } else {
            v.findViewById<TextView>(R.id.txtReqVenue).text = req.venue
            v.findViewById<TextView>(R.id.txtReqAgo).text = req.ago
            v.findViewById<TextView>(R.id.txtReqDate).text = req.date
            v.findViewById<TextView>(R.id.txtReqTime).text = req.time
            v.findViewById<TextView>(R.id.txtReqMessage).text = "“${req.message}”"

            val pending = req.status == RequestStatus.PENDING
            v.findViewById<View>(R.id.layoutReqActions).visibility = if (pending) View.VISIBLE else View.GONE
            val status = v.findViewById<TextView>(R.id.txtReqStatus)
            status.visibility = if (pending) View.GONE else View.VISIBLE
            if (!pending) {
                status.showStatus(if (req.status == RequestStatus.ACCEPTED) BookingStatus.CONFIRMED else BookingStatus.DECLINED)
                status.text = if (req.status == RequestStatus.ACCEPTED) "Accepted" else "Declined"
            }
        }

        v.findViewById<View>(R.id.btnReqAccept).setOnClickListener { onAccept(index) }
        v.findViewById<View>(R.id.btnReqDecline).setOnClickListener { onDecline(index) }
        v.setOnClickListener { onOpen(index) }
    }
}

/** Hourly rows on the owner Schedule: open, booked (dark) or waiting (yellow). */
class ScheduleAdapter(
    private val slots: List<ScheduleSlot>,
    private val onPendingClick: (ScheduleSlot) -> Unit,
    private val onBlockClick: (ScheduleSlot) -> Unit,
) : RecyclerView.Adapter<SimpleVH>() {

    override fun getItemViewType(position: Int) = slots[position].kind.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleVH {
        val layout = when (SlotKind.entries[viewType]) {
            SlotKind.OPEN -> R.layout.item_owner_slot_open
            SlotKind.BOOKED -> R.layout.item_owner_slot_booked
            SlotKind.PENDING -> R.layout.item_owner_slot_pending
        }
        return SimpleVH(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun getItemCount() = slots.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        val slot = slots[position]
        val v = holder.itemView
        v.findViewById<TextView>(R.id.txtSlotTime).text = slot.time
        val card = v.findViewById<View>(R.id.cardSlot)
        when (slot.kind) {
            SlotKind.OPEN -> {
                v.findViewById<View>(R.id.btnBlockSlot).setOnClickListener { onBlockClick(slot) }
            }
            SlotKind.BOOKED -> {
                v.findViewById<TextView>(R.id.txtSlotWho).text = slot.who
                v.findViewById<TextView>(R.id.txtSlotStatus).text = slot.note.ifEmpty { "Confirmed" }
                v.findViewById<TextView>(R.id.txtSlotLength).text = if (slot.hours == 1) "1 hr" else "${slot.hours} hrs"
                // A 2-hour booking spans two rows: 52dp per hour plus the 8dp gap
                val density = v.resources.displayMetrics.density
                card.minimumHeight = ((52 * slot.hours + 8 * (slot.hours - 1)) * density).toInt()
            }
            SlotKind.PENDING -> {
                v.findViewById<TextView>(R.id.txtSlotWho).text = slot.who
                card.setOnClickListener { onPendingClick(slot) }
            }
        }
    }
}
