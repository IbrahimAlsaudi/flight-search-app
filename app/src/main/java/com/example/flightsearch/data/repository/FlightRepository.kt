package com.example.flightsearch.data.repository

import com.example.flightsearch.data.dao.AirportSearchResult
import com.example.flightsearch.data.dao.AirportSuggestion
import com.example.flightsearch.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun getAirports(query: String): Flow<List<AirportSuggestion>>
    fun getAllFavorites(): Flow<List<Favorite>>
    fun getDestinations(departureCode: String): Flow<List<AirportSearchResult>>

    fun isFavorite(departureCode: String, destinationCode: String): Flow<Boolean>

    suspend fun deleteFromFavorite(departureCode: String, destinationCode: String)

    suspend fun addToFavorite(departureCode: String, destinationCode: String)
}