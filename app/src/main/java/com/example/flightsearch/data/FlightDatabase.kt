package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.flightsearch.data.dao.AirportDao
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite
import androidx.room.RoomDatabase
import com.example.flightsearch.data.dao.FavoriteDao

@Database(entities = [Airport::class, Favorite::class], version = 1)
abstract class FlightDatabase: RoomDatabase() {

    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var instance: FlightDatabase? = null

        fun getDatabase(context: Context): FlightDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FlightDatabase::class.java,
                    "flight_database"
                )
                    .createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also {
                        instance = it
                    }
            }
        }
    }
}