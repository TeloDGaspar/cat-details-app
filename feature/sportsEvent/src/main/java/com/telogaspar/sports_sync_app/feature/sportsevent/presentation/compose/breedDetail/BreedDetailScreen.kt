package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.breedDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.telogaspar.core.presentation.viewModel.UiState
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Breed
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.FavoriteSwitch
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel.BreedListViewModel

@Composable
fun BreedDetailScreen(viewModel: BreedListViewModel, breedId: String) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val breed = (uiState as? UiState.Success<List<Breed>>)?.result?.find { it.breedId == breedId }

    breed?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = it.imageUrl,
                contentDescription = it.breedName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(text = it.breedName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = "Origin: ${it.origin}")
            Text(text = "Lifespan: ${it.lifeSpan} years")
            Text(text = "Temperament: ${it.temperament}")
            it.description?.let { description -> Text(text = description) }

            FavoriteSwitch(
                modifier = Modifier.testTag("sport_favorite_${it.breedName}"),
                checked = it.isFavorite,
                toggleFavorite = {
                    viewModel.updateSport(it)
                }
            )
        }
    }


}