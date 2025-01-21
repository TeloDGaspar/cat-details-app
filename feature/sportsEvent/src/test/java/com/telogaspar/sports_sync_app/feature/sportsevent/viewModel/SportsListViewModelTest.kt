package com.telogaspar.sports_sync_app.feature.sportsevent.viewModel

import com.telogaspar.core.presentation.viewModel.UiState
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.exception.SportsNotFoundException
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.SportsFacade
import com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel.SportsListViewModel
import com.telogaspar.sports_sync_app.feature.sportsevent.util.SportsListTestHelper.mockedSportsListViewModel
import io.mockk.MockKAnnotations
import com.telogaspar.core.R
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class SportsListViewModelTest {
    @MockK
    private lateinit var useCase: SportsFacade

    private lateinit var viewModel: SportsListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SportsListViewModel(useCase, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Fetch Sports with Favorites`() = runTest {
        // GIVEN
        val favoriteSports = setOf("sport1")
        val favoriteEvents = setOf("event1", "event3")
        val sportsList = mockedSportsListViewModel

        coEvery { useCase.fetchFavoriteSportListUseCase() } returns flowOf(favoriteSports)
        coEvery { useCase.fetchFavoriteEventListUseCase() } returns flowOf(favoriteEvents)
        coEvery { useCase.fetchSportsListUseCase() } returns flowOf(sportsList)

        // WHEN
        viewModel.fetchSports()
        advanceUntilIdle()

        // THEN
        val expectedFilteredSportsList = listOf(
            Sports(
                "sport1", "Football", listOf(
                    Event("event1", "sport1", "Match1", 1234567890, true)
                ), true
            ), Sports(
                "sport2", "Basketball", listOf(
                    Event("event3", "sport2", "Game1", 1234567892, true),
                    Event("event4", "sport2", "Game2", 1234567893, false)
                ), false
            )
        )

        val job = launch(testDispatcher) {
            viewModel.uiState.collect { uiState ->
                if (uiState is UiState.Success) {
                    assertEquals(expectedFilteredSportsList, uiState.result)
                }
            }
        }

        delay(5000)
        job.cancel()
    }

    @Test
    fun `Update Sport Favorite Status`() = runTest {
        // GIVEN
        val sport = Sports("sport1", "Football", emptyList(), false)
        val favoriteSports = setOf("sport1")
        val favoriteEvents = setOf("event1")
        coEvery { useCase.saveSportFavoriteUseCase(sport.sportId) } returns Unit
        coEvery { useCase.removeSportFavoriteUseCase(sport.sportId) } returns Unit
        coEvery { useCase.fetchFavoriteSportListUseCase() } returns flowOf(favoriteSports)
        coEvery { useCase.fetchFavoriteEventListUseCase() } returns flowOf(favoriteEvents)

        // WHEN
        viewModel.updateSport(sport)
        advanceUntilIdle()

        // THEN
        coVerify { useCase.saveSportFavoriteUseCase(sport.sportId) }
    }

    @Test
    fun `Update Event Favorite Status`() = runTest {
        // GIVEN
        val event = Event("event1", "sport1", "Match1", 1234567890, false)

        val sportsList = mockedSportsListViewModel
        val favoriteEvents = setOf("event1")
        coEvery { useCase.saveEventFavoriteUseCase(event.eventId) } returns Unit
        coEvery { useCase.removeEventFavoriteUseCase(event.eventId) } returns Unit
        coEvery { useCase.fetchFavoriteEventListUseCase() } returns flowOf(favoriteEvents)
        coEvery { useCase.fetchFavoriteSportListUseCase() } returns flowOf(emptySet())
        coEvery { useCase.fetchSportsListUseCase() } returns flowOf(sportsList)

        // WHEN
        viewModel.fetchSports()
        advanceUntilIdle()
        viewModel.updateEvent(event)
        advanceUntilIdle()

        val expectedFilteredSportsList = listOf(
            Sports(
                "sport1", "Football", listOf(
                    Event("event1", "sport1", "Match1", 1234567890, true),
                    Event("event2", "sport1", "Match2", 1234567891, false)
                ), false
            ), Sports(
                "sport2", "Basketball", listOf(
                    Event("event3", "sport2", "Game1", 1234567892, false),
                    Event("event4", "sport2", "Game2", 1234567893, false)
                ), false
            )
        )

        val job = launch(testDispatcher) {
            viewModel.uiState.collect { uiState ->
                if (uiState is UiState.Success) {
                    assertEquals(expectedFilteredSportsList, uiState.result)
                }
            }
        }

        // THEN
        coVerify { useCase.saveEventFavoriteUseCase(event.eventId) }
        delay(5000)
        job.cancel()
    }

    @Test
    fun `handleError sets correct error message for SportsNotFoundException`() = runTest {
        // GIVEN
        val expectedErrorMessage = R.string.sports_not_found_error
        coEvery { useCase.fetchSportsListUseCase() } returns flow {throw SportsNotFoundException()}

        //WHEN
        viewModel.fetchSports()
        advanceUntilIdle()

        // THEN
        val job = launch(testDispatcher) {
            viewModel.uiState.collect { uiState ->
                if (uiState is UiState.Error) {
                    assertEquals(expectedErrorMessage, uiState.errorMessage)
                }
            }
        }
        delay(5000)
        job.cancel()
    }

    @Test
    fun `handleError sets correct error message for IOException`() = runTest {
        // GIVEN
        val expectedErrorMessage = R.string.network_error
        coEvery { useCase.fetchSportsListUseCase() } returns flow {throw IOException()}


        // WHEN
        viewModel.fetchSports()
        advanceUntilIdle()

        val job = launch(testDispatcher) {
            viewModel.uiState.collect { uiState ->
                if (uiState is UiState.Error) {
                    assertEquals(expectedErrorMessage, uiState.errorMessage)
                }
            }
        }
        delay(5000)
        job.cancel()
    }

    @Test
    fun `handleError sets correct error message for unexpected error`() = runTest {
        // GIVEN
        val expectedErrorMessage = R.string.unexpected_error
        coEvery { useCase.fetchSportsListUseCase() } returns flow {throw Exception()}


        // WHEN
        viewModel.fetchSports()
        advanceUntilIdle()

        // THEN
        val job = launch(testDispatcher) {
            viewModel.uiState.collect { uiState ->
                if (uiState is UiState.Error) {
                    assertEquals(expectedErrorMessage, uiState.errorMessage)
                }
            }
        }
        delay(5000)
        job.cancel()
    }
}