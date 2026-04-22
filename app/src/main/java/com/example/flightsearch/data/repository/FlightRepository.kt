package com.example.flightsearch.data.repository

import com.example.flightsearch.data.dao.DestinationInfo
import com.example.flightsearch.data.dao.SearchSuggestion
import com.example.flightsearch.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    fun getAirports(query: String): Flow<List<SearchSuggestion>>
    fun getAllFavorites(): Flow<List<Favorite>>
    fun getDestinations(departureCode: String): Flow<List<DestinationInfo>>

    fun isFavorite(departureCode: String, destinationCode: String): Flow<Boolean>

    suspend fun deleteFromFavorite(departureCode: String, destinationCode: String)

    suspend fun addToFavorite(departureCode: String, destinationCode: String)
}
