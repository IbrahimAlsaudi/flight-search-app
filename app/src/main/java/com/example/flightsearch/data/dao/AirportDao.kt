package com.example.flightsearch.data.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data class to retrieve the destination airport info including favorite status.
 * **/
data class DestinationInfo(
    @ColumnInfo(name = "iata_code")
    val code: String,
    val name: String,
    val isFavorite: Boolean = false
)

/**
 * Data class for search suggestions (autocomplete).
 * **/
data class SearchSuggestion(
    @ColumnInfo(name = "iata_code")
    val code: String,
    val name: String,
)

@Dao
interface AirportDao {

    /**
     * Query to show airports name and code based on the user input
     * */
    @Query("SELECT iata_code, name FROM airport " +
            "WHERE iata_code LIKE '%' || :query || '%' " +
            "OR name LIKE '%' || :query || '%' " +
            "ORDER BY passengers DESC")
    fun getAirports(query: String): Flow<List<SearchSuggestion>>

    /**
     * Query to show all flights available for one airport.
     * Includes a subquery to check if the route is marked as a favorite.
     * */
    @Query("""
        SELECT iata_code, name, (SELECT EXISTS
        (SELECT 1 FROM favorite
        WHERE departure_code = :departureCode 
        AND destination_code = airport.iata_code)) AS isFavorite
        FROM airport 
        WHERE NOT iata_code = :departureCode 
        ORDER BY passengers DESC
    """)
    fun getDestinations(departureCode: String): Flow<List<DestinationInfo>>

}
