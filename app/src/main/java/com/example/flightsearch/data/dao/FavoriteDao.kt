package com.example.flightsearch.data.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.flightsearch.data.entity.Favorite
import kotlinx.coroutines.flow.Flow


data class FavoritesList(
    @ColumnInfo(name = "departure_code")
    val departureCode: String,
    @ColumnInfo(name = "destination_code")
    val destinationCode: String
)


@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("""
        INSERT INTO favorite (departure_code, destination_code)
        VALUES(:departureCode, :destinationCode)
    """
    )
    suspend fun addToFavorite(departureCode: String, destinationCode: String)

    @Query("""
        DELETE FROM favorite
        WHERE departure_code = :departureCode AND destination_code = :destinationCode
    """)
    suspend fun deleteFromFavorite(departureCode: String, destinationCode: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite " +
            "WHERE departure_code = :departureCode " +
            "AND destination_code = :destinationCode)")
    fun isFavorite(departureCode: String, destinationCode: String): Flow<Boolean>
}