package com.telogaspar.sports_sync_app.feature.sportsevent.data.remote

import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.EventResponseItem

interface SportsEventListRemoteDataSource {

    suspend fun fetchSportsEventList(): List<EventResponseItem>
}