package com.telogaspar.sports_sync_app.feature.sportsevent.data.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.EventMapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.SportsEventListRemoteDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


internal class SportListRepositoryImpl(
    private val remoteDataSource: SportsEventListRemoteDataSource,
    private val mapper: EventMapper
) : SportListRepository {
    override fun fetchSportList(): Flow<List<Sports>> {
        return flow {
            val sportsResponse = remoteDataSource.fetchSportsEventList()
            emit(mapper.map(sportsResponse))
        }
    }
}