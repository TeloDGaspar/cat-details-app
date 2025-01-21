package com.telogaspar.sports_sync_app

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Type
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSportListRepository : SportListRepository {
    override fun fetchSportList(): Flow<List<Sports>> {
        return flowOf(
            listOf(
                Sports(
                    sportId = "1", sportName = "Soccer", events = listOf(
                        Event(
                            eventId = "1",
                            sportID = "1",
                            eventName = "Soccer-Match",
                            eventStartTime = 1700000000L,
                            isFavorite = true
                        )
                    ), isFavorite = true
                ), Sports(
                    sportId = "2", sportName = "Basketball", events = listOf(
                        Event(
                            eventId = "2",
                            sportID = "2",
                            eventName = "Basketball-Match",
                            eventStartTime = 1700000000L,
                            isFavorite = false
                        )
                    ), isFavorite = false
                )
            )
        )
    }

    override fun getFavorite(type: Type): Flow<Set<String>> {
        return flowOf(emptySet())
    }

    override suspend fun saveFavorite(eventId: String, type: Type) {
        //fake implementation
    }

    override suspend fun removeFavorite(eventId: String, type: Type) {
        //fake implementation
    }
}