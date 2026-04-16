package com.example.flightsearch.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.flightsearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
    ) 
}