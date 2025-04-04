package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Breed
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import kotlinx.coroutines.flow.Flow

class FetchSportsListUseCase(private val repository: SportListRepository) {

    operator fun invoke(): Flow<List<Breed>> {
        return repository.fetchSportList()
    }
}