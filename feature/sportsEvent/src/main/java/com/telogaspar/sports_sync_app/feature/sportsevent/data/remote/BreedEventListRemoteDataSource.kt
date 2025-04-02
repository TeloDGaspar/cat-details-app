package com.telogaspar.sports_sync_app.feature.sportsevent.data.remote

import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedsResponse

internal interface BreedEventListRemoteDataSource {

    suspend fun fetchBreedList(): List<BreedsResponse>
}