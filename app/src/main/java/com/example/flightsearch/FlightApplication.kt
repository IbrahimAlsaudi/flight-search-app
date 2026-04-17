package com.example.flightsearch

import android.app.Application
import com.example.flightsearch.data.container.FlightContainer
import com.example.flightsearch.data.container.FlightContainerImpl

class FlightApplication: Application() {
    lateinit var container: FlightContainer

    override fun onCreate() {
        super.onCreate()
        container = FlightContainerImpl(this)
    }
}