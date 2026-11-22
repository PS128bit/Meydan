# Meydan

Venue booking app for sports grounds, courts and event spaces. Software for Mobile Devices course project, Team Supernova (Muhammad Anas, Abdul Hadi, Umair Abdullah).

## Phase 2: Frontend (XML layouts)

All 23 screens from the Assignment 1 Figma design, built as XML layouts: the player app (01–16) and the ground owner app (17–23).

| # | Screen | Layout | Activity |
|---|--------|--------|----------|
| 01 | Splash | `activity_splash.xml` | `ui.auth.SplashActivity` |
| 02 | Log in (phone or email + password) | `activity_login.xml` | `ui.auth.LoginActivity` |
| 03 | Sign up (phone required, email optional) | `activity_signup.xml` | `ui.auth.SignupActivity` |
| 04 | Home | `activity_home.xml` | `ui.player.HomeActivity` |
| 05 | Search results | `activity_search_results.xml` | `ui.player.SearchResultsActivity` |
| 06 | Filters | `activity_filters.xml` | `ui.player.FiltersActivity` |
| 07 | Ground details | `activity_venue_details.xml` | `ui.player.VenueDetailsActivity` |
| 08 | Pick a slot | `activity_pick_slot.xml` | `ui.player.PickSlotActivity` |
| 09 | Send request | `activity_send_request.xml` | `ui.player.SendRequestActivity` |
| 10 | Request sent | `activity_request_sent.xml` | `ui.player.RequestSentActivity` |
| 11 | Your matches (my bookings) | `activity_my_bookings.xml` | `ui.player.MyBookingsActivity` |
| 12 | Booking details | `activity_booking_details.xml` | `ui.player.BookingDetailsActivity` |
| 13 | Rate your game | `activity_rate_game.xml` | `ui.player.RateGameActivity` |
| 14 | Saved grounds | `activity_saved.xml` | `ui.player.SavedActivity` |
| 15 | Alerts (notifications) | `activity_notifications.xml` | `ui.player.NotificationsActivity` |
| 16 | Profile | `activity_profile.xml` | `ui.player.ProfileActivity` |
| 17 | Owner home | `activity_owner_home.xml` | `ui.owner.OwnerHomeActivity` |
| 18 | Add ground: details | `activity_owner_add_ground_details.xml` | `ui.owner.AddGroundDetailsActivity` |
| 19 | Add ground: photos and price | `activity_owner_add_ground_photos.xml` | `ui.owner.AddGroundPhotosActivity` |
| 20 | Add ground: availability | `activity_owner_add_ground_availability.xml` | `ui.owner.AddGroundAvailabilityActivity` |
| 21 | Requests | `activity_owner_requests.xml` | `ui.owner.OwnerRequestsActivity` |
| 22 | Request details | `activity_owner_request_details.xml` | `ui.owner.OwnerRequestDetailsActivity` |
| 23 | Schedule | `activity_owner_schedule.xml` | `ui.owner.OwnerScheduleActivity` |

### Reusable layouts

| Layout | Used for |
|--------|----------|
| `item_venue_card.xml` | Ground card in Home and Search results lists |
| `item_ticket.xml` | Booking shown as a match ticket (Home, Your matches) |
| `item_sport_tile.xml` | Sport tiles on Home |
| `item_review.xml` | Reviews on Ground details |
| `item_day.xml` | Date strip on Pick a slot |
| `item_timeline_step.xml` | Booking status timeline (Request sent, Booking details) |
| `item_photo_thumb.xml` | Picked photo on Rate your game |
| `item_saved_venue.xml` | Saved ground tile (2-column grid) |
| `item_notification.xml` | One alert on the Alerts screen |
| `item_owner_request_compact.xml` | Booking request card on Owner home |
| `item_owner_request.xml` | Booking request card on Requests |
| `item_owner_slot_open.xml`, `_booked.xml`, `_pending.xml` | Hour rows on the owner Schedule |
| `view_owner_nav.xml` | Owner floating nav bar (Home, Requests, Schedule, Grounds) |
| `view_player_nav.xml` | Floating bottom navigation bar |
| `view_day_bar.xml` | 24-hour opening hours bar |

### Design system

- **Colours** (`values/colors.xml`): emerald mint, obsidian, charcoal, slate, vault yellow, soft mist.
- **Fonts** (`res/font`): Bricolage Grotesque (headlines), Space Grotesk (prices, dates, times), Inter (body).
- **Styles** (`values/styles.xml`): text styles, buttons, inputs, cards, chips, status badges, nav bar.
- **Drawables**: stroke icons, sport court drawings used as photo placeholders, shapes.

Lists use `RecyclerView` with `tools:listitem`, and sample data uses `tools:text`, so the Design preview shows realistic content.

### Running the app

All player screens are connected, so the app can be clicked through on a phone or emulator:

Splash (1.5 s) → Log in → Home → Search → Ground details → Pick a slot → Send request → Request sent → Your matches → Booking details → Rate your game. The floating nav bar switches between Home, Saved, Bookings, Alerts and Profile.

The owner app opens from Profile → "Own a ground?", or by choosing "List my ground" at sign up: Owner home → Requests → Request details → Accept → Schedule, and Grounds → Add ground steps 1–3 → Publish.

- Navigation helpers: `ui/Nav.kt`
- Sample data and list adapters: `demo/DemoData.kt`, `demo/DemoAdapters.kt`, `demo/OwnerDemoData.kt`, `demo/OwnerAdapters.kt` (temporary, replaced by the local database and server API in the next phase)

## Build

Open the project folder in Android Studio and let Gradle sync. AGP 9.3.3 (built-in Kotlin), Gradle 9.5.0, runs on JDK 17–25. compileSdk 35, minSdk 26.
