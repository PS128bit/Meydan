package com.supernova.meydan.demo

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.supernova.meydan.R

/*
 * Sample data so every screen shows real-looking content on a phone.
 * This is temporary: in the next phase it is replaced by the local database
 * (offline) and the server API (cloud).
 */

data class Sport(val name: String, val count: String, @DrawableRes val court: Int)

data class Venue(
    val name: String,
    val sport: String,
    val sportType: String,
    val area: String,
    val distance: String,
    val address: String,
    val rating: String,
    val reviews: Int,
    val pricePerHour: Int,
    val slotsTonight: Int,
    @DrawableRes val court: Int,
    val size: String,
    val surface: String,
    val courts: Int,
    val owner: String,
    val ownerInitials: String,
    val about: String,
)

enum class BookingStatus(val label: String, @ColorRes val bg: Int, @ColorRes val fg: Int) {
    CONFIRMED("Confirmed", R.color.mint_tint, R.color.mint_dark),
    PENDING("Pending", R.color.yellow_tint, R.color.yellow_dark),
    WAITING_SYNC("Waiting to sync", R.color.mist_2, R.color.slate),
    DECLINED("Declined", R.color.red_tint, R.color.red_dark),
    PLAYED("Played", R.color.mist_2, R.color.slate),
}

data class Booking(
    val venueIndex: Int,
    val day: String,
    val date: String,
    val month: String,
    val fullDate: String,
    val court: String,
    val time: String,
    val hours: Int,
    val status: BookingStatus,
)

data class Review(val initials: String, val name: String, val date: String, val rating: Float, val body: String)

data class Day(val name: String, val number: String)

enum class AlertAction { TICKET, RATE, NONE }

data class Alert(
    val title: String,
    val body: String,
    val time: String,
    @DrawableRes val icon: Int,
    @ColorRes val iconBg: Int,
    @ColorRes val iconTint: Int,
    var unread: Boolean,
    val action: AlertAction,
    val actionLabel: String = "",
)

object DemoData {

    const val USER_NAME = "Hamza Khan"
    const val USER_FIRST_NAME = "Hamza"
    const val USER_INITIALS = "HK"
    const val USER_PHONE = "+92 300 1234567"
    const val OWNER_PHONE = "+92 300 7654321"
    const val LOCATION = "F-11, Islamabad"

    val sports = listOf(
        Sport("Futsal", "24 grounds", R.drawable.court_futsal),
        Sport("Padel", "11 courts", R.drawable.court_padel),
        Sport("Cricket", "9 grounds", R.drawable.court_cricket),
        Sport("Squash", "7 courts", R.drawable.court_squash),
        Sport("Table tennis", "6 halls", R.drawable.court_tabletennis),
        Sport("Swimming", "4 pools", R.drawable.court_pool),
        Sport("Event halls", "12 halls", R.drawable.court_hall),
    )

    val venues = listOf(
        Venue(
            "Kickoff Futsal Arena", "Futsal", "Futsal · Indoor", "F-11 Markaz", "1.2 km",
            "Street 7, F-11 Markaz, Islamabad", "4.8", 126, 3000, 7, R.drawable.court_futsal,
            "5 v 5", "Turf", 3, "Ali Raza", "AR",
            "Covered 5-a-side pitch with new turf, floodlights and seating for 20. Bibs and a ball are free with every booking.",
        ),
        Venue(
            "Smash Padel Club", "Padel", "Padel · Outdoor", "E-11/2", "3.4 km",
            "Main Double Road, E-11/2, Islamabad", "4.7", 88, 4000, 4, R.drawable.court_padel,
            "Doubles", "Glass court", 2, "Sara Malik", "SM",
            "Two glass-walled padel courts with racket rental and a small cafe. Evening slots fill up fast on weekends.",
        ),
        Venue(
            "Margalla Cricket Nets", "Cricket", "Cricket · Nets", "H-8/4", "6.1 km",
            "Near Sports Complex, H-8/4, Islamabad", "4.5", 64, 2500, 9, R.drawable.court_cricket,
            "4 lanes", "Astro", 4, "Bilal Ahmed", "BA",
            "Four practice lanes with a bowling machine on request. Good lights for night sessions.",
        ),
        Venue(
            "Court 7 Squash", "Squash", "Squash · Indoor", "F-7 Markaz", "4.0 km",
            "Jinnah Super, F-7 Markaz, Islamabad", "4.6", 41, 1800, 5, R.drawable.court_squash,
            "Singles", "Maple wood", 2, "Usman Tariq", "UT",
            "Two air-conditioned squash courts with changing rooms and showers.",
        ),
        Venue(
            "Blue Lane Aquatics", "Swimming", "Swimming · Indoor", "G-9 Markaz", "5.2 km",
            "Karachi Company, G-9 Markaz, Islamabad", "4.4", 57, 1500, 6, R.drawable.court_pool,
            "25 m", "Heated", 4, "Hina Shah", "HS",
            "Heated 25 m pool with four lanes. Separate family and ladies timings.",
        ),
    )

    val bookings = listOf(
        Booking(0, "SAT", "23", "NOV", "Sat, 23 Nov", "Court 2", "8:00 – 10:00 PM", 2, BookingStatus.CONFIRMED),
        Booking(1, "TUE", "26", "NOV", "Tue, 26 Nov", "Court 1", "7:00 – 8:00 PM", 1, BookingStatus.PENDING),
        Booking(3, "FRI", "29", "NOV", "Fri, 29 Nov", "Court 1", "6:00 – 7:00 PM", 1, BookingStatus.WAITING_SYNC),
        Booking(0, "SUN", "17", "NOV", "Sun, 17 Nov", "Court 3", "9:00 – 10:00 PM", 1, BookingStatus.PLAYED),
        Booking(2, "WED", "13", "NOV", "Wed, 13 Nov", "Lane 2", "5:00 – 7:00 PM", 2, BookingStatus.DECLINED),
    )

    val reviews = listOf(
        Review("UA", "Umair A.", "Played 2 weeks ago", 5f, "Turf is in great shape and the lights are bright. Ali replied within ten minutes."),
        Review("AH", "Abdul H.", "Played last month", 4f, "Good pitch, a bit warm in the afternoon. Parking was easy."),
        Review("ZK", "Zain K.", "Played last month", 5f, "Booked at 11 PM, confirmed in five minutes. Will come back."),
    )

    val days = listOf(
        Day("THU", "21"), Day("FRI", "22"), Day("SAT", "23"), Day("SUN", "24"),
        Day("MON", "25"), Day("TUE", "26"), Day("WED", "27"),
    )

    val alertsToday = listOf(
        Alert(
            "Ali Raza accepted your request", "Sat 23 Nov · 8 – 10 PM at Kickoff Futsal Arena. You can now call the owner.",
            "2m", R.drawable.ic_check, R.color.mint_tint, R.color.mint_dark, true, AlertAction.TICKET, "See ticket",
        ),
        Alert(
            "Your request is waiting to sync", "Court 7 Squash, Fri 29 Nov. It'll send as soon as you're back online.",
            "1h", R.drawable.ic_sync, R.color.mist_2, R.color.slate, true, AlertAction.NONE,
        ),
    )

    val alertsEarlier = listOf(
        Alert(
            "How was the game?", "Rate Kickoff Futsal Arena and Ali Raza for your match on Sun 17 Nov.",
            "3d", R.drawable.ic_star, R.color.yellow_tint, R.color.yellow_dark, false, AlertAction.RATE, "Rate now",
        ),
        Alert(
            "Margalla Cricket Nets declined", "Wed 13 Nov, 5 – 7 PM was already taken. Try another time or ground.",
            "1w", R.drawable.ic_close, R.color.red_tint, R.color.red_dark, false, AlertAction.NONE,
        ),
        Alert(
            "New padel courts near you", "Smash Padel Club in E-11 is now on Meydan.",
            "2w", R.drawable.ic_pin, R.color.mint_tint, R.color.mint_dark, false, AlertAction.NONE,
        ),
    )
}
