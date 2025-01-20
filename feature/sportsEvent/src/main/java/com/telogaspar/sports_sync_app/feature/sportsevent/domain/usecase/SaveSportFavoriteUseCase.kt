package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.Type

class SaveSportFavoriteUseCase(private val repository: SportListRepository) {
    suspend operator fun invoke(sportId: String) {
        repository.saveFavorite(sportId, Type.FAVORITE_SPORTS)
    }

}
