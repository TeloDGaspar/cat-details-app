package com.telogaspar.sports_sync_app.feature.sportsevent.data.api

import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.EventResponseItem
import retrofit2.http.GET

internal interface SportsEventApi {
    @GET("sports.json")
    suspend fun getEvents(): List<EventResponseItem>
}