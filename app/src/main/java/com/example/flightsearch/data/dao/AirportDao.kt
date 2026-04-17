package com.example.flightsearch.data.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data class to retrieve only the Airport code & name.
 * Don't load entire database into memory at once.
 * The UI only shows name and code, So no need to get the whole Entity
 * **/
data class AirportSearchResult(
    @ColumnInfo(name = "iata_code")
    val code: String,
    val name: String
)

@Dao
interface AirportDao {

    /**
     * Query to show airports name and code based on the user input
     * The || is used for concatenation in kotlin %||query||% equals to %query% in SQL
     * */
    @Query("SELECT iata_code, name FROM airport " +
            "WHERE iata_code LIKE '%' || :query || '%' " +
            "OR name LIKE '%' || :query || '%' " +
            "ORDER BY passengers DESC")
    fun getAirports(query: String): Flow<List<AirportSearchResult>>

    /**
     * Query to show all flights available for one airport.
     * Assumed that every airport can depart to any airport except for itself
     * */
    @Query("SELECT iata_code, name FROM airport " +
            "WHERE NOT iata_code = :departureCode " +
            "ORDER BY passengers DESC")
    fun getFlights(departureCode: String): Flow<List<AirportSearchResult>>

}