package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.compose.favoriteBreed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Breed
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel.BreedListViewModel

@Composable
fun FavoriteBreedsScreen(viewModel: BreedListViewModel) {

    val favoriteBreeds = viewModel.fetchBreedByFavorite() ?: return
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(favoriteBreeds) { breed ->
            FavoriteBreedItem(breed = breed)
        }
    }


}

@Composable
fun FavoriteBreedItem(breed: Breed) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = breed.breedName,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Lifespan: ${breed.lifeSpan} years")
        }
    }
}