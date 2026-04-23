package com.example.flightsearch.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


interface UserPreferencesRepository{
    val userSearchQuery: Flow<String>
    suspend fun saveSearchQuery(query: String)
}
class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): UserPreferencesRepository {

    private companion object {
        val SEARCH_QUERY = stringPreferencesKey("search_query")
        const val TAG = "UserPreferencesRepo"
    }

    override suspend fun saveSearchQuery(query: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_QUERY] = query
        }
    }

    override val userSearchQuery: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[SEARCH_QUERY] ?: ""
        }
}