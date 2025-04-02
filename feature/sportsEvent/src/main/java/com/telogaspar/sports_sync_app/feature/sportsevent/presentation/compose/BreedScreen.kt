package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.telogaspar.core.presentation.viewModel.UiState
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Breed
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel.BreedListViewModel

@Composable
fun BreedScreen(viewModel: BreedListViewModel, onBreedClick: (String) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.fetchSports()
    }
    val sportsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    BreedContent(
        sportsState = sportsUiState,
        onToggleFavoriteSports = { sports ->
            viewModel.updateSport(sports)
        },
        onRetry = { viewModel.fetchSports() },
        onBreedClick = onBreedClick
    )
}

@Composable
fun BreedContent(
    sportsState: UiState<List<Breed>>,
    onToggleFavoriteSports: (Breed) -> Unit,
    onRetry: () -> Unit,
    onBreedClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (sportsState) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is UiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onRetry() }) {
                        Text("Retry")
                    }
                }
            }
            is UiState.Success -> {
                sportsState.result?.let {
                    SportsList(
                        sports = it,
                        onToggleFavoriteSports = onToggleFavoriteSports,
                        onBreedClick = onBreedClick
                    )
                }
            }
        }
    }
}

@Composable
fun SportsList(
    sports: List<Breed>,
    onToggleFavoriteSports: (Breed) -> Unit,
    onBreedClick: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val filteredSports = remember(sports, query) {
        sports.filter { it.breedName.contains(query, ignoreCase = true) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search for a breed...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredSports) { breed ->
                SportItem(
                    sport = breed,
                    onToggleFavoriteSports = onToggleFavoriteSports,
                    onBreedClick = onBreedClick
                )
            }
        }
    }
}

@Composable
fun SportItem(
    sport: Breed,
    onToggleFavoriteSports: (Breed) -> Unit,
    onBreedClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onBreedClick(sport.breedId) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = sport.breedName.uppercase(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onToggleFavoriteSports(sport) }) {
                    Icon(
                        imageVector = if (sport.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (sport.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
            AsyncImage(
                model = sport.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

