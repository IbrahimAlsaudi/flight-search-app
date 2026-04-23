package com.example.flightsearch.data.container

import com.example.flightsearch.data.repository.FlightRepository
import com.example.flightsearch.data.repository.UserPreferencesRepository

interface FlightContainer {
    val flightRepository: FlightRepository
    val userPreferencesRepository: UserPreferencesRepository
}