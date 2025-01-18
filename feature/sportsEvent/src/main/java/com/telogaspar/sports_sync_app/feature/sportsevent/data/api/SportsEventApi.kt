package com.telogaspar.sports_sync_app.feature.sportsevent.data.api

import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.EventResponseItem
import retrofit2.http.GET

interface SportsEventApi {
    @GET("sports.json") // Replace with your actual endpoint path
    suspend fun getEvents(): List<EventResponseItem>
}