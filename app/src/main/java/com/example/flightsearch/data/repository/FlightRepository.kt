package com.example.flightsearch.data.repository

import com.example.flightsearch.data.dao.AirportSearchResult
import com.example.flightsearch.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun getAirports(query: String): Flow<List<AirportSearchResult>>

    fun getFlights(departureCode: String): Flow<List<AirportSearchResult>>

    fun isFavorite(departureCode: String, destinationCode: String): Flow<Boolean>

    suspend fun deleteFromFavorite(favorite: Favorite)

    suspend fun addToFavorite(favorite: Favorite)
}