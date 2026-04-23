package com.example.flightsearch.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightApplication

object AppViewModelProvider  {
    val factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            MainViewModel(
                flightRepository = flightApplication().container.flightRepository,
                userPreferencesRepository = flightApplication().container.userPreferencesRepository
            )
        }
    }
}

fun CreationExtras.flightApplication(): FlightApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FlightApplication)



