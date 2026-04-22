package com.example.flightsearch.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp



@Composable
fun FavoriteCard(
    departureCode: String,
    destinationCode: String,
    deleteFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(Modifier.weight(1f)) {
                FlightInfoSection(
                    label = "DEPART",
                    code = departureCode,

                )
                Spacer(Modifier.padding(vertical = 4.dp))
                FlightInfoSection(
                    label = "ARRIVE",
                    code = destinationCode,

                )
            }
            IconButton(onClick = deleteFavorite) {
                Icon(
                    imageVector = Icons.Filled.Star ,
                    contentDescription = "Remove from favorites",
                    tint = Color(0xFFFFD700)
                )
            }
        }
    }
}


@Composable
fun FlightCard(
    departCode: String,
    departName: String,
    arriveCode: String,
    arriveName: String,
    onFavouriteClicked: () -> Unit,
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(Modifier.weight(1f)) {
                FlightInfoSection(
                    label = "DEPART",
                    code = departCode,
                    name = departName
                )
                Spacer(Modifier.padding(vertical = 4.dp))
                FlightInfoSection(
                    label = "ARRIVE",
                    code = arriveCode,
                    name = arriveName
                )
            }
            IconButton(onClick = onFavouriteClicked) {
                Icon(
                    imageVector = if (isFavourite) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = if (isFavourite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavourite) Color(0xFFFFD700) else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
private fun FlightInfoSection(
    label: String,
    code: String,
    modifier: Modifier = Modifier,
    name: String = "",
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 2.dp)
        ) {
            Text(
                text = code,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
