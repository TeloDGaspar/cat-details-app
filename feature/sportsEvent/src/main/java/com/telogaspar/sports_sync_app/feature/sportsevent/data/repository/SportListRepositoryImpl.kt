package com.telogaspar.sports_sync_app.feature.sportsevent.data.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.EventMapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.toBreedDao
import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.toDomainModel
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.BreedEventListRemoteDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Breed
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Type
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.exception.SportsNotFoundException
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.BreedListLocalDataBase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow


internal class SportListRepositoryImpl(
    private val remoteDataSource: BreedEventListRemoteDataSource,
    private val mapper: EventMapper,
    private val localDataSource: FavoriteListLocalDataSource,
    private val breedListLocalDataBase: BreedListLocalDataBase
) : SportListRepository {

    override fun fetchSportList(): Flow<List<Breed>> = flow {
        try {
            // Try fetching from API
            val sportsResponse = remoteDataSource.fetchBreedList()
            if (sportsResponse.isNotEmpty()) {
                val mappedSports = mapper.map(sportsResponse)

                breedListLocalDataBase.insertSports(mappedSports.map { it.toBreedDao() })

                emit(mappedSports)
                return@flow
            }
        } catch (e: Exception) {
            val localSports = breedListLocalDataBase.getSports().firstOrNull()
            if (!localSports.isNullOrEmpty()) {
                emit(localSports.map { it.toDomainModel() })
                return@flow
            } else {
                throw SportsNotFoundException()
            }
        }
    }

    override suspend fun saveFavorite(eventId: String, type: Type) {
        localDataSource.saveFavorite(eventId, type)
    }

    override suspend fun removeFavorite(eventId: String, type: Type) {
        localDataSource.removeFavorite(eventId, type)
    }

    override fun getFavorite(type: Type): Flow<Set<String>> {
        return localDataSource.getFavorite(type)
    }
}