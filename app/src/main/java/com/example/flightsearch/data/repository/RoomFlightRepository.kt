package com.example.flightsearch.data.repository

import com.example.flightsearch.data.dao.AirportDao
import com.example.flightsearch.data.dao.AirportSearchResult
import com.example.flightsearch.data.dao.FavoriteDao
import com.example.flightsearch.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

class RoomFlightRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
) : FlightRepository {
    override fun getAirports(query: String): Flow<List<AirportSearchResult>> = airportDao.getAirports(query)

    override fun getFlights(departureCode: String): Flow<List<AirportSearchResult>> = airportDao.getFlights(departureCode)

    override fun isFavorite(
        departureCode: String,
        destinationCode: String
    ): Flow<Boolean> = favoriteDao.isFavorite(departureCode, destinationCode)

    override suspend fun deleteFromFavorite(favorite: Favorite) = favoriteDao.deleteFromFavorite(favorite)

    override suspend fun addToFavorite(favorite: Favorite) = favoriteDao.addToFavorite(favorite)
}