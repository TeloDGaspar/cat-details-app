package com.telogaspar.sports_sync_app.feature.sportsevent

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.telogaspar.core.presentation.UiState
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.SportsLoading
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel.SportsListViewModel

@Composable
fun SportScreen(viewModel: SportsListViewModel = hiltViewModel()) {
    val sportsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.fetchSports()
    }

    SportsContent(sportsState = sportsUiState, onToggleFavorite = { event ->
        val updatedEvent = event.copy(isFavorite = !event.isFavorite)
        viewModel.updateEvent(updatedEvent)
    })
}

@Composable
fun SportsContent(
    sportsState: UiState<List<Sports>>, onToggleFavorite: (Event) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (sportsState) {
            is UiState.Loading -> {
                SportsLoading()
            }

            is UiState.Error -> {/*RetryButton(
                    messageError = stockDetailState.errorMessage,
                    onRetry = { onRetry.invoke() })*/
            }

            is UiState.Success -> {
                sportsState.result?.let {
                    SportsList(sports = it, onToggleFavorite = onToggleFavorite)
                }
            }
        }
    }
}

@Composable
fun SportsList(
    sports: List<Sports>, onToggleFavorite: (Event) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sports) { sport ->
            SportItem(sport = sport, onToggleFavorite = onToggleFavorite)
        }
    }
}

@Composable
fun SportItem(
    sport: Sports, onToggleFavorite: (Event) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showOnlyFavorites by remember { mutableStateOf(false) } // Local state for filtering favorites

    LaunchedEffect(Unit) {
        /*
        sportsListViewModel.startTimer()
        */
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(Color.Red, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sport.sportName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            FavoriteSwitch(checked = showOnlyFavorites,
                onCheckedChange = { showOnlyFavorites = it })

            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }


        if (isExpanded) {
            val eventsToDisplay = if (showOnlyFavorites) {
                sport.events.filter { it.isFavorite }
            } else {
                sport.events
            }
            Log.i("Devlog","eventsToDisplay: ${sport.events.filter { it.isFavorite }}")

            EventGrid(events = eventsToDisplay, toggleFavorite = { event ->
                onToggleFavorite(event)
            })
        }
    }
}

@Composable
fun EventGrid(events: List<Event>, toggleFavorite: (Event) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4), // Maximum of 4 columns
        modifier = Modifier
            .height(400.dp)
            .background(Color(0xFF343434), shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(events) { event ->
            EventItem(event, toggleFavorite)
        }
    }
}

@Composable
fun EventItem(event: Event, toggleFavorite: (Event) -> Unit) {
    var showOnlyFavorites by remember { mutableStateOf(false) }
    val splitStrings = event.eventName.split("-")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = event.eventStartTime.toString(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))
        FavoriteSwitch(checked = showOnlyFavorites,
            onCheckedChange = { showOnlyFavorites = it },
            toggleFavorite = { toggleFavorite(event) })
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = buildAnnotatedString {
                append(splitStrings[0])
                append("\n")
                withStyle(style = SpanStyle(color = Color(0xFFe7410f))) {
                    append(" vs ")
                }
                append("\n")
                append(splitStrings[1])
            },
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}


@Composable
fun FavoriteSwitch(
    checked: Boolean, onCheckedChange: (Boolean) -> Unit, toggleFavorite: (() -> Unit)? = null
) {
    val icon = if (checked) {
        Icons.Default.Favorite // Custom icon when checked
    } else {
        Icons.Default.FavoriteBorder // Custom icon when unchecked
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            if (toggleFavorite != null) {
                toggleFavorite()
            }
            onCheckedChange(!checked)
        }) {
            Icon(
                imageVector = icon,
                contentDescription = if (checked) "Show favorites" else "Show all events"
            )
        }
    }
}

