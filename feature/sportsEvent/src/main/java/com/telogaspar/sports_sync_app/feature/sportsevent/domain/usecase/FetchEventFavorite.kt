package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Type
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import kotlinx.coroutines.flow.Flow

class FetchFavoriteEventListUseCase(private val repository: SportListRepository) {
    operator fun invoke(): Flow<Set<String>> {
        return repository.getFavorite(Type.FAVORITE_EVENTS)
    }
}
