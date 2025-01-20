package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.Type

class RemoveSportFavoriteUseCase(private val repository: FavoriteListLocalDataSource) {
    suspend operator fun invoke(sportId: String) {
        repository.removeFavorite(sportId, Type.FAVORITE_SPORTS)
    }
}
