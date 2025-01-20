package com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteListLocalDataSource {
    suspend fun saveFavorite(eventId: String, type: Type)
    suspend fun removeFavorite(eventId: String, type: Type)
    fun getFavorite(type: Type): Flow<Set<String>>
}

enum class Type{
    FAVORITE_EVENTS,
    FAVORITE_SPORTS
}