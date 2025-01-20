package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.Type

class RemoveEventFavoriteUseCase(private val repository: FavoriteListLocalDataSource) {
    suspend operator fun invoke(eventId: String) {
        repository.removeFavorite(eventId, Type.FAVORITE_EVENTS)
    }
}
