package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Type
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository

class RemoveEventFavoriteUseCase(private val repository: SportListRepository) {
    suspend operator fun invoke(eventId: String) {
        repository.removeFavorite(eventId, Type.FAVORITE_EVENTS)
    }
}
