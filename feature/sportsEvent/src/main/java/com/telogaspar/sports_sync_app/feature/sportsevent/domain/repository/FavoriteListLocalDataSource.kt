package com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Type
import kotlinx.coroutines.flow.Flow

internal interface FavoriteListLocalDataSource {
    suspend fun saveFavorite(eventId: String, type: Type)
    suspend fun removeFavorite(eventId: String, type: Type)
    fun getFavorite(type: Type): Flow<Set<String>>
}
