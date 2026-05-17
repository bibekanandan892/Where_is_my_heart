# Where Is My Heart

A two-person location-sharing companion app for Android — built so a couple, family members, or close friends can quietly see *"where my person is right now"* without the noise of a full-blown social app.

Two phones pair once. After that, each phone publishes its live location to the other through Firebase, and a Compose UI surfaces the partner's position on a map plus presence signals (online / last seen). Push notifications wake the app when the partner moves into or out of a named place.

---

## Why this project

This was a personal exploration of what a *minimum-viable* location app needs to feel trustworthy:

- **Pairing without accounts** — no signup form, no friend list. One QR/code scan and you're linked for life.
- **Battery-respecting updates** — location is published only when meaningfully different from the last point, not on a fixed timer.
- **Presence first, history second** — the UI answers "are they OK right now" before "where were they at 4pm yesterday."
- **Notifications you don't want to mute** — only fired on real transitions (home → away, away → home), not every GPS tick.

---

## Tech stack

- **Language:** Kotlin
- **UI:** Jetpack Compose, Material 3, Compose Navigation
- **DI:** Hilt
- **Backend:** Firebase Cloud Messaging (push), Realtime Database (location publish/subscribe)
- **Serialization:** kotlinx.serialization
- **Build:** Gradle, JDK 17, `compileSdk 33`, `minSdk 24`

A small companion Ktor server lives in [`com.example.wimh-server`](https://github.com/bibekanandan892/com.example.wimh-server) and handles pairing tokens + notification fan-out.

---

## How it works

```
   ┌────────────┐                         ┌────────────┐
   │  Phone A   │                         │  Phone B   │
   │            │                         │            │
   │  publish   │──► Firebase RTDB ◄──────│  subscribe │
   │  location  │                         │            │
   │            │◄── Firebase RTDB ◄──────│  publish   │
   │  subscribe │                         │  location  │
   └─────┬──────┘                         └──────┬─────┘
         │           ┌──────────────┐            │
         │           │   FCM push   │            │
         └──────────►│  (presence,  │◄───────────┘
                     │  geofence)   │
                     └──────────────┘
```

1. **Pair once.** Phone A generates a one-time code, Phone B enters it. The Ktor server hands both phones the same `chat_id` and stores their FCM tokens.
2. **Publish.** Each phone writes its current `{lat, lng, timestamp, battery}` into the shared RTDB node under that `chat_id`.
3. **Subscribe.** Each phone listens on the partner's node and animates the marker.
4. **Notify.** When a phone crosses a saved geofence (home, work) the server fans out an FCM push to the partner so the partner is notified even when the app is in the background.

---

## Build & run

```bash
git clone https://github.com/bibekanandan892/Where_is_my_heart.git
cd Where_is_my_heart
./gradlew assembleDebug
```

> Drop your own `google-services.json` into `app/` before building — the repo doesn't ship one. Any free Firebase project with RTDB + FCM enabled will work.

Install on a device:

```bash
./gradlew installDebug
```

---

## Project layout

```
app/src/main/java/com/petpack/whereismyheart/
├── ui/             # Compose screens (pair, map, settings)
├── data/           # Firebase RTDB + FCM data sources
├── domain/         # Pairing / location / presence models
├── di/             # Hilt modules
└── service/        # FCM messaging service, foreground location service
```

---

## Roadmap

- Offline-first: cache the partner's last known location so the map isn't blank when launched on no-signal.
- E2E-encrypt the location payload — Firebase should only see ciphertext.
- "Safe places" library so geofences can be created by long-pressing the map instead of typing addresses.
- Apple Watch / Wear OS companion that just shows distance + direction.

---

## License

MIT — see [LICENSE](LICENSE).
