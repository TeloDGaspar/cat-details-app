package com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper

import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedDao
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Breed

fun BreedDao.toDomainModel(): Breed {
    return Breed(
        breedId = this.breedId,
        breedName = this.breedName,
        imageUrl = this.imageUrl,
        lifeSpan = this.lifeSpan,
        origin = this.origin,
        temperament = this.temperament,
        description = this.description,
        isFavorite = this.isFavorite
    )
}

fun Breed.toBreedDao(): BreedDao {
    return BreedDao(
        breedId = this.breedId,
        breedName = this.breedName,
        imageUrl = this.imageUrl,
        lifeSpan = this.lifeSpan,
        origin = this.origin,
        temperament = this.temperament,
        description = this.description,
        isFavorite = this.isFavorite
    )
}