package com.example.flightsearch.data.container

import android.content.Context
import com.example.flightsearch.data.FlightDatabase
import com.example.flightsearch.data.repository.FlightRepository
import com.example.flightsearch.data.repository.RoomFlightRepository

class FlightContainerImpl(context: Context) : FlightContainer {
    override val flightRepository: FlightRepository by lazy {
        RoomFlightRepository(
            airportDao = FlightDatabase.getDatabase(context).airportDao(),
            favoriteDao = FlightDatabase.getDatabase(context).favoriteDao()
        )
    }
}