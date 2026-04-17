package com.example.flightsearch.data.container

import com.example.flightsearch.data.repository.FlightRepository

interface FlightContainer {
    val flightRepository: FlightRepository
}