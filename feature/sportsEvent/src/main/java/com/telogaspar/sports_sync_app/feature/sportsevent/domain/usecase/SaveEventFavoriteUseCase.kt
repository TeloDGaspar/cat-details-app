package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.Type

class SaveEventFavoriteUseCase(private val repository: FavoriteListLocalDataSource) {
    suspend operator fun invoke(eventId: String) {
        repository.saveFavorite(eventId, Type.FAVORITE_EVENTS)
    }

}
