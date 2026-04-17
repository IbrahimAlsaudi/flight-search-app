package com.example.flightsearch.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.flightsearch.data.entity.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addToFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFromFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite " +
            "WHERE departure_code = :departureCode " +
            "AND destination_code = :destinationCode")
    fun isFavorite(departureCode: String, destinationCode: String): Flow<Boolean>
}