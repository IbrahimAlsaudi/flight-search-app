package com.example.flightsearch.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.flightsearch.ui.components.FlightCard
import com.example.flightsearch.ui.components.MyTextField
import com.example.flightsearch.ui.components.MyTopAppBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            MyTopAppBar()
        }
    ) { innerPadding ->
        Column(modifier = modifier
            .padding(innerPadding)
            .padding(top = 8.dp)
        ) {
            MyTextField(
                value = "",
                onValueChanged = {},
                onIconClicked = {},
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(20) {
                    FlightCard(
                        departCode = "SFO",
                        departName = "San Francisco International",
                        arriveCode = "JFK",
                        arriveName = "John F. Kennedy International",
                        onFavouriteClicked = {},
                        isFavourite = true
                    )
                }
            }
        }
    }
}







