package com.telogaspar.sports_sync_app.feature.sportsevent.data.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.EventMapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.BreedEventListRemoteDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.BreedListLocalDataBase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test


class SportListRepositoryImplTest {

    @MockK
    private lateinit var remoteDataSource: BreedEventListRemoteDataSource

    @MockK
    private lateinit var localDataSource: FavoriteListLocalDataSource

    private lateinit var mapper: EventMapper
    private lateinit var repository: SportListRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mapper = EventMapper()
        val breedListLocalDataBase: BreedListLocalDataBase = mockk(relaxed = true)
        repository = SportListRepositoryImpl(remoteDataSource, mapper, localDataSource, breedListLocalDataBase)
    }

    @Test
    fun `fetchSportsList empty response throws EmptySportsListException`() = runTest {
        // GIVEN
        coEvery { remoteDataSource.fetchBreedList() } returns emptyList()
        coEvery { localDataSource.getFavorite(any()) } returns flowOf(emptySet())

        // WHEN
        val result = repository.fetchSportList()

        // THEN
        assertThrows(NoSuchElementException::class.java) {
            runBlocking {
                result.last()
            }
        }
        coVerify { remoteDataSource.fetchBreedList() }
    }
}