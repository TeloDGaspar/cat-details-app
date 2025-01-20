package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material.icons.filled.SportsBaseball
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.telogaspar.core.presentation.viewModel.UiState
import com.telogaspar.core.presentation.compose.RetryButton
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.filled.SportsFootball
import androidx.compose.material.icons.filled.SportsGolf
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material.icons.filled.SportsHockey
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material.icons.filled.SportsVolleyball
import androidx.compose.material.icons.filled.Sports

import com.telogaspar.sports_sync_app.feature.sportsevent.R
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
            viewModel.updateEvent(event.copy(isFavorite = !event.isFavorite))
        },
        onToggleFavoriteSports = { sports ->
            viewModel.updateSport(sports.copy(isFavorite = !sports.isFavorite))
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
    Box(modifier = Modifier.fillMaxSize()) {
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
            FavoriteSwitch(checked = sport.isFavorite,
                toggleFavorite = { onToggleFavoriteSports(sport) })

            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }


        if (isExpanded) {
            val eventsToDisplay = if (sport.isFavorite) {
                sport.events.filter { it.isFavorite }
            } else {
                sport.events
            }
            EventColumn(
                sport,
                eventsToDisplay,
                toggleFavorite = { event ->
                    onToggleFavorite(event)
                })
            /*EventGrid(events = eventsToDisplay, toggleFavorite = { event ->
                onToggleFavorite(event)
            })*/
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CountDown(
            modifier = Modifier.padding(4.dp),
            timeStamp = event.eventStartTime,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        FavoriteSwitch(checked = event.isFavorite, toggleFavorite = { toggleFavorite(event) })
        Spacer(modifier = Modifier.height(8.dp))
        EventText(
            eventName = event.eventName,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
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
            EventItem2(sport, event, toggleFavorite)
        }
    }
}


@Composable
fun EventItem2(sport: Sports, event: Event, toggleFavorite: (Event) -> Unit) {
    Row(
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
                timeStamp = event.eventStartTime,
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
        "tennis" ,"tabletennis" -> Icons.Default.SportsTennis
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
