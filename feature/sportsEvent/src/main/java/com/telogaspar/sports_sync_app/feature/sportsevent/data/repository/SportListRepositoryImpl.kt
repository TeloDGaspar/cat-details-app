package com.telogaspar.sports_sync_app.feature.sportsevent.data.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.EventMapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.SportsEventListRemoteDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.exception.SportsNotFoundException
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map


internal class SportListRepositoryImpl(
    private val remoteDataSource: SportsEventListRemoteDataSource,
    private val mapper: EventMapper,
    private val localDataSource: FavoriteListLocalDataSource
) : SportListRepository {
    override fun fetchSportList(): Flow<List<Sports>> {
        return flow {
            val sportsResponse = remoteDataSource.fetchSportsEventList()
            val favoriteSports = localDataSource.getFavorite(Type.FAVORITE_SPORTS).first()
            val favoriteEvents = localDataSource.getFavorite(Type.FAVORITE_EVENTS).first()
            val sportsList = mapper.map(sportsResponse).map { sports ->
                sports.copy(
                    isFavorite = favoriteSports.contains(sports.sportId),
                    events = sports.events.map { event ->
                        event.copy(isFavorite = favoriteEvents.contains(event.eventId))
                    }
                )
            }
            if (sportsResponse.isEmpty()) {
                throw SportsNotFoundException()
            }
            emit(sportsList)
        }
    }
}