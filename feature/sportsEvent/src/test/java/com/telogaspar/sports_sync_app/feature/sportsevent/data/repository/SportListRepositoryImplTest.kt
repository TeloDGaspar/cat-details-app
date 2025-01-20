package com.telogaspar.sports_sync_app.feature.sportsevent.data.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.EventMapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.EventResponseItem
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.SportsEventListRemoteDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.exception.SportsNotFoundException
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import com.telogaspar.sports_sync_app.feature.sportsevent.util.EventsListHelper.mockedEventResponseItem
import com.telogaspar.sports_sync_app.feature.sportsevent.util.EventsListHelper.mockedSportsList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test


class SportListRepositoryImplTest {

    @MockK
    private lateinit var remoteDataSource: SportsEventListRemoteDataSource

    @MockK
    private lateinit var localDataSource: FavoriteListLocalDataSource

    private lateinit var mapper: EventMapper
    private lateinit var repository: SportListRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mapper = EventMapper()
        repository = SportListRepositoryImpl(remoteDataSource, mapper, localDataSource)
    }

    @Test
    fun `fetchSportList returns mapped sports`() = runTest {
        //GIVEN
        coEvery { remoteDataSource.fetchSportsEventList() } returns mockedEventResponseItem
        coEvery { localDataSource.getFavorite(any()) } returns flowOf(emptySet())

        //WHEN
        val result = repository.fetchSportList()

        //THEN
        result.collect { sportList ->
            assertEquals(mockedSportsList, sportList)

        }
        coVerify { remoteDataSource.fetchSportsEventList() }
    }

    @Test
    fun `fetchSportsList empty response throws EmptySportsListException`() = runTest {
        // GIVEN
        coEvery { remoteDataSource.fetchSportsEventList() } returns emptyList()
        coEvery { localDataSource.getFavorite(any()) } returns flowOf(emptySet())

        // WHEN
        val result = repository.fetchSportList()

        // THEN
        assertThrows(SportsNotFoundException::class.java) {
            runBlocking {
                result.last()
            }
        }
        coVerify { remoteDataSource.fetchSportsEventList() }
    }
}