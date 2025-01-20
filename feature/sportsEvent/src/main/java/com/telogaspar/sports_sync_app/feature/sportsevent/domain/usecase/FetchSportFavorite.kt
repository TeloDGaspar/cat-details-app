package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.Type
import kotlinx.coroutines.flow.Flow

class FetchFavoriteSportListUseCase(private val repository: SportListRepository) {
    operator fun invoke(): Flow<Set<String>> {
        return repository.getFavorite(Type.FAVORITE_SPORTS)
    }

}
