# ✈️ Flight Search

A simple Android app that lets users search for flights between airports and save their favorite routes — built with Jetpack Compose and Room.

---

## Features

- Real-time airport search using SQLite
- Built entirely with Jetpack Compose, featuring smooth animations for list additions and removals.
- Save and manage favorite routes
- Search query persists across app sessions using DataStore

---
## Screenshots


https://github.com/user-attachments/assets/64d6c212-f633-4318-b462-16b1df462195


---
## Tech Stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose |
| Architecture | MVVM |
| Database | Room |
| Reactive Data | Kotlin Flow + StateFlow |
| Persistence | Preferences DataStore |
| Language | Kotlin |

---

## Project Structure

```
com.example.flightsearch
├── data
│   ├── container    # Dependency Injection (FlightContainer)
│   ├── dao          # Room DAOs (Airport & Favorite)
│   ├── entity       # Database Entities
│   └── repository   # Abstract & Concrete Repository implementations
├── ui
│   ├── components   # Reusable Compose components (Cards, TextFields)
│   ├── screens      # Main UI screens (HomeScreen)
│   └── viewmodel    # App Logic & State management (MainViewModel)
```

---

## How It Works

- The app ships with a pre-populated airport database
- Every airport has flights to every other airport in the database
- Favorites are stored locally using Room and persist across sessions
- Search query is saved with DataStore and restored on relaunch

---

## Architecture

The app follows MVVM with a clean separation of layers:

```
Room Database
    └── DAO
            └── Repository
                    └── ViewModel (StateFlow + flatMapLatest + combine)
                            └── Compose UI
```

Data flows reactively — any change in the database automatically updates the UI with no manual refresh needed.

---



