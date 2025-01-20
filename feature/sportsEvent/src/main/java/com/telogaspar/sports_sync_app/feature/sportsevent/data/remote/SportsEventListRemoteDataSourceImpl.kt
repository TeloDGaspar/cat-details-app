package com.telogaspar.sports_sync_app.feature.sportsevent.data.remote

import com.telogaspar.sports_sync_app.feature.sportsevent.data.api.SportsEventApi
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.EventResponseItem

internal class SportsEventListRemoteDataSourceImpl(
    private val sportsEventApi: SportsEventApi
) : SportsEventListRemoteDataSource {
    override suspend fun fetchSportsEventList(): List<EventResponseItem> {
        return sportsEventApi.getEvents()
    }
}