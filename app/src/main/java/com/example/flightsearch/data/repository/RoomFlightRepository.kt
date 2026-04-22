package com.example.flightsearch.data.repository

import com.example.flightsearch.data.dao.AirportDao
import com.example.flightsearch.data.dao.AirportSearchResult
import com.example.flightsearch.data.dao.AirportSuggestion
import com.example.flightsearch.data.dao.FavoriteDao
import com.example.flightsearch.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

class RoomFlightRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
) : FlightRepository {

    override fun getAllFavorites(): Flow<List<Favorite>> = favoriteDao.getAllFavorites()
    override fun getAirports(query: String): Flow<List<AirportSuggestion>> = airportDao.getAirports(query)

    override fun getDestinations(departureCode: String): Flow<List<AirportSearchResult>> = airportDao.getDestinations(departureCode)

    override fun isFavorite(
        departureCode: String,
        destinationCode: String
    ): Flow<Boolean> = favoriteDao.isFavorite(departureCode, destinationCode)

    override suspend fun deleteFromFavorite(departureCode: String,destinationCode: String) = favoriteDao.deleteFromFavorite(departureCode, destinationCode)

    override suspend fun addToFavorite(departureCode: String,destinationCode: String) = favoriteDao.addToFavorite(departureCode, destinationCode)
}