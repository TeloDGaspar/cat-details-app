package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Type
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository

class SaveEventFavoriteUseCase(private val repository: SportListRepository) {
    suspend operator fun invoke(eventId: String) {
        repository.saveFavorite(eventId, Type.FAVORITE_EVENTS)
    }

}
