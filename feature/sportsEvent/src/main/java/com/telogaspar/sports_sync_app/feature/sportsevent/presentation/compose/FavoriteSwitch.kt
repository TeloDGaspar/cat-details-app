package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoriteSwitch(
    modifier: Modifier = Modifier, checked: Boolean, toggleFavorite: () -> Unit
) {
    val icon = if (checked) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    IconButton(modifier = modifier, onClick = {
        toggleFavorite()
    }) {
        Icon(
            imageVector = icon,
            contentDescription = if (checked) "Show favorites" else "Show all events"
        )
    }
}