package com.supernova.meydan.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.supernova.meydan.R
import com.supernova.meydan.ui.rupees
import com.supernova.meydan.ui.showStatus
import com.supernova.meydan.ui.tintBackground
import com.supernova.meydan.ui.tintImage

/*
 * RecyclerView adapters for the demo data. Each one fills an item_*.xml layout.
 * In the next phase the same adapters can take data from the database instead.
 */

class SimpleVH(view: View) : RecyclerView.ViewHolder(view)

private fun ViewGroup.inflate(@LayoutRes layout: Int): View =
    LayoutInflater.from(context).inflate(layout, this, false)

/** Sport tiles on Home. */
class SportAdapter(
    private val items: List<Sport>,
    private val onClick: (Sport) -> Unit,
) : RecyclerView.Adapter<SimpleVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleVH(parent.inflate(R.layout.item_sport_tile))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        val sport = items[position]
        val v = holder.itemView
        v.findViewById<ImageView>(R.id.imgSportCourt).setImageResource(sport.court)
        v.findViewById<TextView>(R.id.txtSportName).text = sport.name
        v.findViewById<TextView>(R.id.txtSportCount).text = sport.count
        v.setOnClickListener { onClick(sport) }
    }
}

/** Ground cards on Home and Search results. [onClick] gets the index into DemoData.venues. */
class VenueAdapter(
    private val items: List<Venue>,
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<SimpleVH>() {

    private val saved = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleVH(parent.inflate(R.layout.item_venue_card))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        val venue = items[position]
        val v = holder.itemView
        v.findViewById<ImageView>(R.id.imgVenue).setImageResource(venue.court)
        v.findViewById<TextView>(R.id.txtVenueSport).text = venue.sport.uppercase()
        v.findViewById<TextView>(R.id.txtVenueName).text = venue.name
        v.findViewById<TextView>(R.id.txtVenueArea).text = "${venue.area} · ${venue.distance}"
        v.findViewById<TextView>(R.id.txtVenueRating).text = venue.rating
        v.findViewById<TextView>(R.id.txtVenueReviews).text = "(${venue.reviews})"
        v.findViewById<TextView>(R.id.txtVenuePrice).text = rupees(venue.pricePerHour)
        v.findViewById<TextView>(R.id.txtVenueSlotsLeft).text = "${venue.slotsTonight} slots tonight"

        val heart = v.findViewById<ImageView>(R.id.btnSave)
        fun drawHeart() {
            val on = position in saved
            heart.setImageResource(if (on) R.drawable.ic_heart_filled else R.drawable.ic_heart)
            heart.tintImage(if (on) R.color.red else R.color.slate)
        }
        drawHeart()
        heart.setOnClickListener {
            if (!saved.add(position)) saved.remove(position)
            drawHeart()
        }
        v.setOnClickListener { onClick(DemoData.venues.indexOf(venue)) }
    }
}

/** Match tickets on Home and Your matches. [onClick] gets the index into DemoData.bookings. */
class TicketAdapter(
    private var items: List<Booking>,
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<SimpleVH>() {

    fun submit(newItems: List<Booking>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleVH(parent.inflate(R.layout.item_ticket))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        val booking = items[position]
        bindTicket(holder.itemView, booking)
        holder.itemView.findViewById<View>(R.id.cardTicket).setOnClickListener {
            onClick(DemoData.bookings.indexOf(booking))
        }
    }

    companion object {
        /** Fills an item_ticket.xml view. Also used for the "Your next match" ticket on Home. */
        fun bindTicket(v: View, booking: Booking) {
            val venue = DemoData.venues[booking.venueIndex]
            v.findViewById<TextView>(R.id.txtTicketDay).text = booking.day
            v.findViewById<TextView>(R.id.txtTicketDate).text = booking.date
            v.findViewById<TextView>(R.id.txtTicketMonth).text = booking.month
            v.findViewById<TextView>(R.id.txtTicketSport).text = "${venue.sport} · ${booking.court}"
            v.findViewById<TextView>(R.id.txtTicketStatus).showStatus(booking.status)
            v.findViewById<TextView>(R.id.txtTicketVenue).text = venue.name
            v.findViewById<TextView>(R.id.txtTicketTime).text = booking.time
            val hours = if (booking.hours == 1) "1 hour" else "${booking.hours} hours"
            v.findViewById<TextView>(R.id.txtTicketMeta).text =
                "$hours · ${rupees(venue.pricePerHour * booking.hours)} at the ground"
        }
    }
}

/** Reviews on Ground details. */
class ReviewAdapter(private val items: List<Review>) : RecyclerView.Adapter<SimpleVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleVH(parent.inflate(R.layout.item_review))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        val review = items[position]
        val v = holder.itemView
        v.findViewById<TextView>(R.id.txtReviewerInitials).text = review.initials
        v.findViewById<TextView>(R.id.txtReviewerName).text = review.name
        v.findViewById<TextView>(R.id.txtReviewDate).text = review.date
        v.findViewById<RatingBar>(R.id.ratingReview).rating = review.rating
        v.findViewById<TextView>(R.id.txtReviewBody).text = review.body
    }
}

/**
 * Date strip. Dark version (Pick a slot): selected day turns mint.
 * [light] version (owner Schedule): white cells, selected day turns obsidian.
 */
class DayAdapter(
    private val items: List<Day>,
    private var selected: Int,
    private val light: Boolean = false,
    private val onSelect: (Day) -> Unit,
) : RecyclerView.Adapter<SimpleVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleVH(parent.inflate(R.layout.item_day))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        val day = items[position]
        val v = holder.itemView
        val isSelected = position == selected
        val bg: Int
        val nameColor: Int
        val numberColor: Int
        if (light) {
            bg = if (isSelected) R.color.obsidian else R.color.white
            nameColor = if (isSelected) R.color.mint else R.color.slate
            numberColor = if (isSelected) R.color.white else R.color.obsidian
        } else {
            bg = if (isSelected) R.color.mint else R.color.charcoal_raised
            nameColor = if (isSelected) R.color.obsidian else R.color.slate_light
            numberColor = if (isSelected) R.color.obsidian else R.color.white
        }
        v.findViewById<TextView>(R.id.txtDayName).apply {
            text = day.name
            setTextColor(context.getColor(nameColor))
        }
        v.findViewById<TextView>(R.id.txtDayNumber).apply {
            text = day.number
            setTextColor(context.getColor(numberColor))
        }
        v.tintBackground(bg)
        v.setOnClickListener {
            val old = selected
            selected = holder.bindingAdapterPosition
            notifyItemChanged(old)
            notifyItemChanged(selected)
            onSelect(items[selected])
        }
    }
}

/** Picked photos on Rate your game. Tapping the x removes a photo. */
class PhotoThumbAdapter(private val courts: MutableList<Int>) : RecyclerView.Adapter<SimpleVH>() {

    fun add(court: Int) {
        courts.add(court)
        notifyItemInserted(courts.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleVH(parent.inflate(R.layout.item_photo_thumb))

    override fun getItemCount() = courts.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.imgThumb).setImageResource(courts[position])
        holder.itemView.findViewById<ImageButton>(R.id.btnRemovePhoto).setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                courts.removeAt(pos)
                notifyItemRemoved(pos)
            }
        }
    }
}

/** Saved ground tiles. Tapping the heart removes the ground from the list. */
class SavedAdapter(
    private val items: MutableList<Venue>,
    private val onClick: (Int) -> Unit,
    private val onChanged: (count: Int) -> Unit,
) : RecyclerView.Adapter<SimpleVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleVH(parent.inflate(R.layout.item_saved_venue))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        val venue = items[position]
        val v = holder.itemView
        v.findViewById<ImageView>(R.id.imgSaved).setImageResource(venue.court)
        v.findViewById<TextView>(R.id.txtSavedSport).text = venue.sport.uppercase()
        v.findViewById<TextView>(R.id.txtSavedName).text = venue.name
        v.findViewById<TextView>(R.id.txtSavedArea).text = "${venue.area} · ${venue.distance}"
        v.findViewById<TextView>(R.id.txtSavedPrice).text = rupees(venue.pricePerHour)
        v.findViewById<TextView>(R.id.txtSavedRating).text = venue.rating
        v.findViewById<ImageButton>(R.id.btnUnsave).setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                items.removeAt(pos)
                notifyItemRemoved(pos)
                onChanged(items.size)
            }
        }
        v.setOnClickListener { onClick(DemoData.venues.indexOf(venue)) }
    }
}

/** Alerts list. [onAction] runs when the alert's button (See ticket / Rate now) is tapped. */
class AlertAdapter(
    private val items: List<Alert>,
    private val onAction: (AlertAction) -> Unit,
) : RecyclerView.Adapter<SimpleVH>() {

    fun markAllRead() {
        items.forEach { it.unread = false }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleVH(parent.inflate(R.layout.item_notification))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        val alert = items[position]
        val v = holder.itemView
        v.findViewById<ImageView>(R.id.imgAlertIcon).apply {
            setImageResource(alert.icon)
            tintBackground(alert.iconBg)
            tintImage(alert.iconTint)
        }
        v.findViewById<TextView>(R.id.txtAlertTitle).text = alert.title
        v.findViewById<TextView>(R.id.txtAlertTime).text = alert.time
        v.findViewById<TextView>(R.id.txtAlertBody).text = alert.body
        v.findViewById<View>(R.id.dotAlertUnread).visibility = if (alert.unread) View.VISIBLE else View.GONE
        val button = v.findViewById<MaterialButton>(R.id.btnAlertAction)
        if (alert.action == AlertAction.NONE) {
            button.visibility = View.GONE
        } else {
            button.visibility = View.VISIBLE
            button.text = alert.actionLabel
            button.setOnClickListener { onAction(alert.action) }
        }
        v.setOnClickListener {
            alert.unread = false
            notifyItemChanged(holder.bindingAdapterPosition)
            if (alert.action != AlertAction.NONE) onAction(alert.action)
        }
    }
}
