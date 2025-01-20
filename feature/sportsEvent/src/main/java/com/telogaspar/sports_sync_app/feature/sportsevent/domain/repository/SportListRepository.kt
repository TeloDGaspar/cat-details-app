package com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import kotlinx.coroutines.flow.Flow

interface SportListRepository {
    fun fetchSportList(): Flow<List<Sports>>
}