package com.telogaspar.sports_sync_app.feature.sportsevent.data.remote

import com.telogaspar.sports_sync_app.feature.sportsevent.data.api.BreedsEventApi
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedsResponse

internal class BreedEventListRemoteDataSourceImpl(
    private val breedsEventApi: BreedsEventApi
) : BreedEventListRemoteDataSource {
    override suspend fun fetchBreedList(): List<BreedsResponse> {
        return breedsEventApi.getBreeds()
    }
}