package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.exception.SportsNotFoundException
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import com.telogaspar.sports_sync_app.feature.sportsevent.util.EventsListHelper.mockedSportsList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test


class FetchSportsListTest {
    @MockK
    private lateinit var repository: SportListRepository

    private lateinit var useCase: FetchSportsListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = FetchSportsListUseCase(repository)
    }

    @Test
    fun `invoke returns list of sports`(): Unit = runTest {
        // GIVEN
        coEvery { repository.fetchSportList() } returns flowOf(mockedSportsList)

        // WHEN
        val result = useCase()

        // THEN
        result.collect {
            assertEquals(mockedSportsList, it)
        }
    }

    @Test
    fun `invoke with empty list throws EmptySportsListException`(): Unit = runTest {
        // GIVEN
        coEvery { repository.fetchSportList() } throws  SportsNotFoundException()

        // WHEN & THEN
        assertThrows(SportsNotFoundException::class.java) {
            useCase.invoke()
        }
    }
}