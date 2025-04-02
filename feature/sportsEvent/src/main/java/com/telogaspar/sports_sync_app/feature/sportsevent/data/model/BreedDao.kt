package com.telogaspar.sports_sync_app.feature.sportsevent.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed_table")
data class BreedDao(
    @PrimaryKey val breedId: String,
    val breedName: String,
    val imageUrl: String?,
    val lifeSpan: String?,
    val origin: String?,
    val temperament: String?,
    val description: String?,
    val isFavorite: Boolean = false
)