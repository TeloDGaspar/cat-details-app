package com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.data.local.CatDao
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BreedListLocalDataBaseImpl @Inject constructor(private val catDao: CatDao) : BreedListLocalDataBase{
    override fun getSports(): Flow<List<BreedDao>> {
        return catDao.getAllSports()
    }

    override suspend fun insertSports(sports: List<BreedDao>) {
        catDao.insertAll(sports)
    }
}