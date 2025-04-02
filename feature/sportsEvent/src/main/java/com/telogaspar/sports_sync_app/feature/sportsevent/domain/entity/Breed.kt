package com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity

data class Breed(
    val breedId: String,
    val breedName: String,
    val imageUrl: String?,
    val lifeSpan: String?,
    val origin: String?,
    val temperament: String?,
    val description: String?,
    val isFavorite: Boolean = false
)
