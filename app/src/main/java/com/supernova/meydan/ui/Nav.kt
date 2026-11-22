package com.supernova.meydan.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.supernova.meydan.R
import com.supernova.meydan.demo.BookingStatus
import com.supernova.meydan.ui.player.HomeActivity
import com.supernova.meydan.ui.player.MyBookingsActivity
import com.supernova.meydan.ui.player.NotificationsActivity
import com.supernova.meydan.ui.player.ProfileActivity
import com.supernova.meydan.ui.player.SavedActivity
import com.supernova.meydan.ui.owner.AddGroundDetailsActivity
import com.supernova.meydan.ui.owner.OwnerHomeActivity
import com.supernova.meydan.ui.owner.OwnerRequestsActivity
import com.supernova.meydan.ui.owner.OwnerScheduleActivity
import java.util.Locale

/* Shared navigation and UI helpers used by every screen. */

const val EXTRA_VENUE = "extra_venue"
const val EXTRA_BOOKING = "extra_booking"
const val EXTRA_SPORT = "extra_sport"
const val EXTRA_HOURS = "extra_hours"
const val EXTRA_SLOTS = "extra_slots"
const val EXTRA_DAY = "extra_day"
const val EXTRA_REQUEST = "extra_request"

/** Opens another screen. Usage: navigate(VenueDetailsActivity::class.java) { putExtra(EXTRA_VENUE, 0) } */
fun Activity.navigate(cls: Class<out Activity>, extras: Intent.() -> Unit = {}) {
    startActivity(Intent(this, cls).apply(extras))
}

/** Goes back to Home, closing every screen opened on top of it. */
fun Activity.goHome() {
    startActivity(
        Intent(this, HomeActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    )
}

/** Starts a fresh task (used after log in / sign up / log out so Back can't return). */
fun Activity.startFresh(cls: Class<out Activity>) {
    startActivity(
        Intent(this, cls).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    )
    finish()
}

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.colorOf(@ColorRes res: Int): Int = ContextCompat.getColor(this, res)

fun rupees(amount: Int): String = String.format(Locale.US, "Rs %,d", amount)

/** Wires the floating player nav bar. [selected] is the tab of the current screen. */
fun Activity.setupPlayerNav(selected: Int) {
    val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
    nav.selectedItemId = selected // set before the listener so it doesn't navigate
    nav.setOnItemSelectedListener { item ->
        if (item.itemId == selected) return@setOnItemSelectedListener true
        val target: Class<out Activity> = when (item.itemId) {
            R.id.nav_home -> HomeActivity::class.java
            R.id.nav_saved -> SavedActivity::class.java
            R.id.nav_bookings -> MyBookingsActivity::class.java
            R.id.nav_alerts -> NotificationsActivity::class.java
            else -> ProfileActivity::class.java
        }
        startActivity(
            Intent(this, target).addFlags(
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NO_ANIMATION
            )
        )
        false // keep this screen's tab highlighted; the opened screen highlights its own
    }
}

/** Wires the floating owner nav bar (Home, Requests, Schedule, Grounds). */
fun Activity.setupOwnerNav(selected: Int) {
    val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
    nav.selectedItemId = selected
    nav.setOnItemSelectedListener { item ->
        if (item.itemId == selected) return@setOnItemSelectedListener true
        val target: Class<out Activity> = when (item.itemId) {
            R.id.nav_owner_home -> OwnerHomeActivity::class.java
            R.id.nav_owner_requests -> OwnerRequestsActivity::class.java
            R.id.nav_owner_schedule -> OwnerScheduleActivity::class.java
            else -> AddGroundDetailsActivity::class.java
        }
        startActivity(
            Intent(this, target).addFlags(
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NO_ANIMATION
            )
        )
        false
    }
}

/** Colours a status badge (Confirmed, Pending, ...). */
fun TextView.showStatus(status: BookingStatus) {
    text = status.label
    backgroundTintList = ColorStateList.valueOf(context.colorOf(status.bg))
    setTextColor(context.colorOf(status.fg))
}

fun ImageView.tintImage(@ColorRes color: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(context.colorOf(color)))
}

fun View.tintBackground(@ColorRes color: Int) {
    backgroundTintList = ColorStateList.valueOf(context.colorOf(color))
}

/** Fills one step of a status timeline (item_timeline_step.xml). */
fun bindTimelineStep(step: View, title: String, time: String, done: Boolean, last: Boolean) {
    step.findViewById<TextView>(R.id.txtStepTitle).text = title
    step.findViewById<TextView>(R.id.txtStepTime).text = time
    val dot = step.findViewById<ImageView>(R.id.imgStepDot)
    dot.setImageResource(if (done) R.drawable.ic_check else R.drawable.ic_clock)
    dot.tintBackground(if (done) R.color.mint else R.color.mist_2)
    dot.tintImage(if (done) R.color.obsidian else R.color.slate)
    step.findViewById<View>(R.id.viewStepLine).visibility = if (last) View.INVISIBLE else View.VISIBLE
}
