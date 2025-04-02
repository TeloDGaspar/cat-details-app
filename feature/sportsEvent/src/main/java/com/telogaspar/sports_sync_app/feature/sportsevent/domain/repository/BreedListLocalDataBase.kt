package com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedDao
import kotlinx.coroutines.flow.Flow

interface BreedListLocalDataBase {
    fun getSports(): Flow<List<BreedDao>>
    suspend fun insertSports(sports: List<BreedDao>)
}