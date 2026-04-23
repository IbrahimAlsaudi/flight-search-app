package com.example.flightsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.dao.DestinationInfo
import com.example.flightsearch.data.dao.SearchSuggestion
import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.data.repository.FlightRepository
import com.example.flightsearch.data.repository.UserPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MainViewModel(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {

            val savedQuery = userPreferencesRepository.userSearchQuery.first()
            if(_searchQuery.value.isEmpty()) {
                _searchQuery.value = savedQuery
            }

        }

        viewModelScope.launch {
            _searchQuery.debounce(1000L)
                .distinctUntilChanged()
                .collect { query ->
                    userPreferencesRepository.saveSearchQuery(query)
                }
        }
    }
    fun onQueryDeleted() {
        _departureAirport.value = null
        _searchQuery.value = ""
    }

    fun onQueryChanged(newQuery: String) {
        _departureAirport.value = null
        _searchQuery.value = newQuery
    }

    // LIST OF ALL FAVORITE FLIGHTS
    @OptIn(ExperimentalCoroutinesApi::class)
    private val favoriteList: Flow<List<Favorite>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                flightRepository.getAllFavorites()
            } else {
                flowOf(emptyList())
            }
        }

    //LIST OF SUGGESTED AIRPORTS
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val searchSuggestions: Flow<List<SearchSuggestion>> = _searchQuery
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                flowOf(emptyList())
            } else {
                flightRepository.getAirports(query)
            }
        }

    private val _departureAirport = MutableStateFlow<SearchSuggestion?>(null)

    fun onDepartureAirportChanged(newAirport: SearchSuggestion) {
        _departureAirport.value = newAirport
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val availableDestinations: Flow<List<DestinationInfo>> =
        _departureAirport.flatMapLatest { airport ->
            if (airport == null) {
                flowOf(emptyList())
            } else {
                flightRepository.getDestinations(airport.code)
            }
        }

    val uiState: StateFlow<HomeUiState> =
        combine(
            _searchQuery, favoriteList, searchSuggestions,
            _departureAirport, availableDestinations
        ) { query, favorites, suggestions, departure, destinations ->
            HomeUiState(
                searchQuery = query,
                favorites = favorites,
                searchSuggestions = suggestions,
                departureAirport = departure,
                availableDestinations = destinations,
                stateHandler = StateHandler.Success
            )
        }.catch { e ->
            emit(HomeUiState(stateHandler = StateHandler.Error(e.message ?: "Unknown Error")))
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeUiState()
            )


    suspend fun addToFavorite(departureCode: String, destinationCode: String) {
        flightRepository.addToFavorite(departureCode, destinationCode)
    }

    suspend fun deleteFromFavorite(departureCode: String, destinationCode: String) {
        flightRepository.deleteFromFavorite(departureCode, destinationCode)
    }

}

data class HomeUiState(
    val searchQuery: String = "",
    val favorites: List<Favorite> = emptyList(),
    val searchSuggestions: List<SearchSuggestion> = emptyList(),
    val departureAirport: SearchSuggestion? = null,
    val availableDestinations: List<DestinationInfo> = emptyList(),
    val stateHandler: StateHandler = StateHandler.Loading
)

sealed interface StateHandler {
    data class Error(val message: String) : StateHandler
    data object Loading : StateHandler
    data object Success : StateHandler
}