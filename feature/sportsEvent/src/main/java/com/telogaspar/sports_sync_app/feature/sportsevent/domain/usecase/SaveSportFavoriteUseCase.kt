package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Type
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository

class SaveSportFavoriteUseCase(private val repository: SportListRepository) {
    suspend operator fun invoke(sportId: String) {
        repository.saveFavorite(sportId, Type.FAVORITE_SPORTS)
    }

}
