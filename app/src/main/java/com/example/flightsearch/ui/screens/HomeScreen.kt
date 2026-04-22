package com.example.flightsearch.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.dao.DestinationInfo
import com.example.flightsearch.data.dao.SearchSuggestion
import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.ui.components.FavoriteCard
import com.example.flightsearch.ui.components.FlightCard
import com.example.flightsearch.ui.components.MyListItem
import com.example.flightsearch.ui.components.MyTextField
import com.example.flightsearch.ui.components.MyTopAppBar
import com.example.flightsearch.ui.viewmodel.AppViewModelProvider
import com.example.flightsearch.ui.viewmodel.HomeUiState
import com.example.flightsearch.ui.viewmodel.MainViewModel
import com.example.flightsearch.ui.viewmodel.StateHandler
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.factory),
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            MyTopAppBar()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(top = 8.dp)
        ) {
            
            MyTextField(
                value = uiState.value.searchQuery,
                onValueChanged = viewModel::onQueryChanged,
                onSearchClicked = {
                    if (uiState.value.searchQuery.isNotEmpty()) {
                        focusManager.clearFocus()
                    }
                },
                onDeleteClicked = viewModel::onQueryDeleted,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            when(uiState.value.stateHandler) {
                
                is StateHandler.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is StateHandler.Error -> {
                    Text(
                        text = (uiState.value.stateHandler as StateHandler.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is StateHandler.Success -> {
                    HomeContent(
                        uiState = uiState.value,
                        onDeleteFavorite = { dep, dest ->
                            coroutineScope.launch {
                                viewModel.deleteFromFavorite(dep, dest)
                            }
                        },
                        onDepartureAirportChanged = { viewModel.onDepartureAirportChanged(it) },
                        onToggleFavorite = { dep, dest, isFav ->
                            coroutineScope.launch {
                                if (isFav) {
                                    viewModel.deleteFromFavorite(dep, dest)
                                } else {
                                    viewModel.addToFavorite(dep, dest)
                                }
                            }
                        }
                    )
                }
            }
            
        }
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onDeleteFavorite: (String, String) -> Unit,
    onDepartureAirportChanged: (SearchSuggestion) -> Unit,
    onToggleFavorite: (String, String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.searchQuery.isEmpty()) {

            Column(modifier = modifier) {
                Text(
                    text = "Favorite routes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )

                FavoriteList(
                    favorites = uiState.favorites,
                    onDeleteFavorite = onDeleteFavorite
                )
            }

    } else if (uiState.departureAirport == null) {
        AirportSuggestionList(
            suggestions = uiState.searchSuggestions,
            onAirportClick = onDepartureAirportChanged,
            modifier = modifier
        )
    } else {
        Column(modifier = modifier) {
            Text(
                text = "Flights from ${uiState.departureAirport.code}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            FlightList(
                departureAirport = uiState.departureAirport,
                destinations = uiState.availableDestinations,
                onFavoriteClick = onToggleFavorite
            )
        }
    }
}

@Composable
private fun FavoriteList(
    favorites: List<Favorite>,
    onDeleteFavorite: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(favorites) { favorite ->
            FavoriteCard(
                departureCode = favorite.departureCode,
                destinationCode = favorite.destinationCode,
                deleteFavorite = { onDeleteFavorite(favorite.departureCode, favorite.destinationCode) }
            )
        }
    }
}

@Composable
private fun AirportSuggestionList(
    suggestions: List<SearchSuggestion>,
    onAirportClick: (SearchSuggestion) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(suggestions) { airport ->
            MyListItem(
                iataCode = airport.code,
                name = airport.name,
                onClick = { onAirportClick(airport) }
            )
        }
    }
}

@Composable
private fun FlightList(
    departureAirport: SearchSuggestion,
    destinations: List<DestinationInfo>,
    onFavoriteClick: (String, String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(destinations) { arrivalAirport ->
            FlightCard(
                departCode = departureAirport.code,
                departName = departureAirport.name,
                arriveCode = arrivalAirport.code,
                arriveName = arrivalAirport.name,
                onFavouriteClicked = {
                    onFavoriteClick(departureAirport.code, arrivalAirport.code, arrivalAirport.isFavorite)
                },
                isFavourite = arrivalAirport.isFavorite
            )
        }
    }
}
