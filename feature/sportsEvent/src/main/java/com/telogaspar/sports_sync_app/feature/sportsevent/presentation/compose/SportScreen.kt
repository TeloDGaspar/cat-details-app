package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsBaseball
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.filled.SportsFootball
import androidx.compose.material.icons.filled.SportsGolf
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material.icons.filled.SportsHockey
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material.icons.filled.SportsVolleyball
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.telogaspar.core.presentation.compose.RetryButton
import com.telogaspar.core.presentation.viewModel.UiState
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel.SportsListViewModel

@Composable
fun SportScreen(viewModel: SportsListViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchSports()
    }
    val sportsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    SportsContent(
        sportsState = sportsUiState,
        onToggleFavorite = { event ->
            viewModel.updateEvent(event)
        },
        onToggleFavoriteSports = { sports ->
            viewModel.updateSport(sports)
        },
        onRetry = { viewModel.fetchSports() })
}

@Composable
fun SportsContent(
    sportsState: UiState<List<Sports>>,
    onToggleFavorite: (Event) -> Unit,
    onToggleFavoriteSports: (Sports) -> Unit,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("sport_screen")
    ) {
        when (sportsState) {
            is UiState.Loading -> {
                SportsLoading()
            }

            is UiState.Error -> {
                RetryButton(
                    messageError = sportsState.errorMessage,
                    onRetry = { onRetry.invoke() }
                )
            }

            is UiState.Success -> {
                sportsState.result?.let {
                    SportsList(
                        sports = it,
                        onToggleFavorite = onToggleFavorite,
                        onToggleFavoriteSports = onToggleFavoriteSports
                    )
                }
            }
        }
    }
}

@Composable
fun SportsList(
    sports: List<Sports>,
    onToggleFavorite: (Event) -> Unit,
    onToggleFavoriteSports: (Sports) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        items(sports) { sport ->
            SportItem(
                sport = sport,
                onToggleFavorite = onToggleFavorite,
                onToggleFavoriteSports = onToggleFavoriteSports
            )
        }
    }
}

@Composable
fun SportItem(
    sport: Sports, onToggleFavorite: (Event) -> Unit, onToggleFavoriteSports: (Sports) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("sport_item_${sport.sportName}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sport.sportName.uppercase(),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF141414),
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            FavoriteSwitch(modifier = Modifier.testTag("sport_favorite_${sport.sportName}"),
                checked = sport.isFavorite,
                toggleFavorite = { onToggleFavoriteSports(sport) })

            IconButton(modifier = Modifier.testTag("arrow_${sport.sportName}"),onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }

        if (isExpanded) {
            EventColumn(
                sport,
                sport.events,
                toggleFavorite = { event ->
                    onToggleFavorite(event)
                })
        }
    }
}

@Composable
fun EventColumn(sport: Sports, events: List<Event>, toggleFavorite: (Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .heightIn(max = 500.dp)
            .fillMaxWidth()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(events) { event ->
            EventItem(sport, event, toggleFavorite)
        }
    }
}


@Composable
fun EventItem(sport: Sports, event: Event, toggleFavorite: (Event) -> Unit) {
    Row(
        modifier = Modifier.testTag("event_item_${event.eventName}"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .background(color = Color(0xFFF0F2F5), shape = RoundedCornerShape(size = 8.dp)),
            imageVector = getSportIcon(sport.sportName),
            contentDescription = "image description"
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            EventText(
                modifier = Modifier.testTag("event_name_${event.eventName}"),
                eventName = event.eventName,
                color = Color(0xFF141414),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF141414),
                ),
                textAlign = TextAlign.Start
            )
            CountDown(
                modifier = Modifier.testTag("event_time_${event.eventName}"),
                timeStamp = event.eventStartTime,
                eventId = event.eventId,
                color = Color(0xFF3D4D5C),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF3D4D5C),
                ),
                textAlign = TextAlign.Start
            )
        }
        FavoriteSwitch(modifier = Modifier
            .testTag("sport_favorite_${event.eventName}")
            .padding(1.dp)
            .width(24.dp)
            .height(24.dp),
            checked = event.isFavorite,
            toggleFavorite = { toggleFavorite(event) })
    }
}

fun getSportIcon(sportName: String): ImageVector {
    return when (sportName.lowercase()) {
        "soccer", "futsal" -> Icons.Default.SportsSoccer
        "basketball" -> Icons.Default.SportsBasketball
        "tennis", "tabletennis" -> Icons.Default.SportsTennis
        "football" -> Icons.Default.SportsFootball
        "baseball" -> Icons.Default.SportsBaseball
        "hockey", "icehockey" -> Icons.Default.SportsHockey
        "cricket" -> Icons.Default.SportsCricket
        "volleyball" -> Icons.Default.SportsVolleyball
        "golf" -> Icons.Default.SportsGolf
        "handball" -> Icons.Default.SportsHandball
        else -> Icons.Default.Sports
    }
}
