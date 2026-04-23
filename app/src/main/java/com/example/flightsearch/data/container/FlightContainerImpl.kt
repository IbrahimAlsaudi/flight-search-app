package com.example.flightsearch.data.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.FlightDatabase
import com.example.flightsearch.data.repository.FlightRepository
import com.example.flightsearch.data.repository.RoomFlightRepository
import com.example.flightsearch.data.repository.UserPreferencesRepository
import com.example.flightsearch.data.repository.UserPreferencesRepositoryImpl

private const val USER_PREFERENCE_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)
class FlightContainerImpl(context: Context) : FlightContainer {
    override val flightRepository: FlightRepository by lazy {
        RoomFlightRepository(
            airportDao = FlightDatabase.getDatabase(context).airportDao(),
            favoriteDao = FlightDatabase.getDatabase(context).favoriteDao()
        )
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepositoryImpl(context.dataStore)
    }

}