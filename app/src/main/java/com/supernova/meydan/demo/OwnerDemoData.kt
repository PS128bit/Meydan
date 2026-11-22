package com.supernova.meydan.demo

/*
 * Sample data for the owner screens (matches the Figma owner flow).
 * Temporary: replaced by the local database and server API in the next phase.
 */

enum class RequestStatus { PENDING, ACCEPTED, DECLINED }

data class OwnerRequest(
    val initials: String,
    val name: String,
    val playerMeta: String,
    val venue: String,
    val date: String,
    val time: String,
    val shortTime: String,
    val hours: Int,
    val price: String,
    val message: String,
    val ago: String,
    var status: RequestStatus,
) {
    val firstName: String get() = name.substringBefore(' ')
}

enum class SlotKind { OPEN, BOOKED, PENDING }

data class ScheduleSlot(
    val time: String,
    val kind: SlotKind,
    val who: String = "",
    val hours: Int = 1,
    val note: String = "",
    val requestIndex: Int = -1,
)

object OwnerDemoData {

    const val OWNER_FIRST_NAME = "Usman"
    const val GROUND = "Kickoff Futsal Arena"

    val requests = mutableListOf(
        OwnerRequest("HK", "Hamza Khan", "On Meydan since 2025 · 3 bookings", GROUND, "Fri 13 Nov",
            "7:00 – 9:00 PM", "7–9 PM", 2, "Rs 8,000", "Group of 10, could you keep bibs ready?", "5m", RequestStatus.PENDING),
        OwnerRequest("BA", "Bilal Ahmed", "On Meydan since 2026 · 1 booking", GROUND, "Sat 14 Nov",
            "10:00 – 11:00 PM", "10–11 PM", 1, "Rs 5,000", "Need the court for our office team.", "22m", RequestStatus.PENDING),
        OwnerRequest("SM", "Sara Malik", "On Meydan since 2025 · 7 bookings", GROUND, "Sun 15 Nov",
            "5:00 – 6:00 PM", "5–6 PM", 1, "Rs 4,000", "Can we bring our own ball?", "1h", RequestStatus.PENDING),
        OwnerRequest("ZA", "Zain Abbas", "New on Meydan", GROUND, "Fri 13 Nov",
            "9:00 – 10:00 PM", "9–10 PM", 1, "Rs 4,000", "First time playing here, see you Friday!", "2h", RequestStatus.PENDING),
        OwnerRequest("AR", "Ali Raza", "On Meydan since 2024 · 21 bookings", GROUND, "Fri 13 Nov",
            "5:00 – 6:00 PM", "5–6 PM", 1, "Rs 4,000", "Same as last week, thanks!", "1d", RequestStatus.ACCEPTED),
        OwnerRequest("UF", "Umar Farooq", "On Meydan since 2025 · 5 bookings", GROUND, "Fri 13 Nov",
            "6:00 – 7:00 PM", "6–7 PM", 1, "Rs 4,000", "Office game.", "1d", RequestStatus.ACCEPTED),
        OwnerRequest("DK", "Danish Kamal", "On Meydan since 2026 · 2 bookings", GROUND, "Sat 14 Nov",
            "8:00 – 9:00 PM", "8–9 PM", 1, "Rs 5,000", "Can we pay half now?", "2d", RequestStatus.DECLINED),
    )

    fun pending() = requests.filter { it.status == RequestStatus.PENDING }

    /** Friday 13 Nov on the owner's schedule (Figma 23). */
    val fridaySchedule = listOf(
        ScheduleSlot("4 PM", SlotKind.OPEN),
        ScheduleSlot("5 PM", SlotKind.BOOKED, "Ali Raza"),
        ScheduleSlot("6 PM", SlotKind.BOOKED, "Umar Farooq"),
        ScheduleSlot("7 PM", SlotKind.BOOKED, "Hamza Khan", hours = 2, note = "Confirmed · 7–9 PM"),
        ScheduleSlot("9 PM", SlotKind.PENDING, "Zain Abbas", requestIndex = 3),
        ScheduleSlot("10 PM", SlotKind.OPEN),
        ScheduleSlot("11 PM", SlotKind.OPEN),
    )

    val scheduleDays = listOf(
        Day("THU", "12"), Day("FRI", "13"), Day("SAT", "14"), Day("SUN", "15"),
        Day("MON", "16"), Day("TUE", "17"), Day("WED", "18"),
    )
}
