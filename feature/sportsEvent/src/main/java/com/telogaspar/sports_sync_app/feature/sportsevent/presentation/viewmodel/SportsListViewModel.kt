package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telogaspar.core.R
import com.telogaspar.core.presentation.viewModel.UiState
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.exception.SportsNotFoundException
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.SportsFacade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SportsListViewModel @Inject constructor(
    private val useCase: SportsFacade, private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Sports>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Sports>>> = _uiState

    private var _listSports: List<Sports>? = null

    fun fetchSports() {
        viewModelScope.launch(dispatcher) {
            useCase.fetchSportsListUseCase().onStart {
                _uiState.value = UiState.Loading
            }.catch { error ->
                handleError(error)
            }.collect { sports ->
                handleSportsSuccess(sports)
            }
        }
    }

    private fun handleSportsSuccess(sports: List<Sports>) {
        _listSports = sports
        filterSport()
    }

    private fun handleError(error: Throwable) {
        _uiState.value = UiState.Error(getErrorMessage(error))
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch(dispatcher) {
            val eventId = event.eventId
            val isFavorite = !event.isFavorite

            if (isFavorite) {
                useCase.saveEventFavoriteUseCase(eventId)
            } else {
                useCase.removeEventFavoriteUseCase(eventId)
            }

            filterEvents()
        }
    }

    fun updateSport(sport: Sports) {
        viewModelScope.launch(dispatcher) {
            val sportId = sport.sportId
            val isFavorite = !sport.isFavorite

            if (isFavorite) {
                useCase.saveSportFavoriteUseCase(sportId)
            } else {
                useCase.removeSportFavoriteUseCase(sportId)
            }
            filterSport()
        }
    }

    private fun filterSport() {
        viewModelScope.launch(dispatcher) {
            combine(
                useCase.fetchFavoriteSportListUseCase(),
                useCase.fetchFavoriteEventListUseCase()
            ) { favoriteSports, favoriteEvents ->
                Pair(favoriteSports, favoriteEvents)
            }.collect { (favoriteSports, favoriteEvents) ->
                val filteredSportsList = arrayListOf<Sports>()
                _listSports?.forEach { sport ->
                    val updatedEvents = sport.events.map { event ->
                        event.copy(isFavorite = favoriteEvents.contains(event.eventId))
                    }
                    val filteredEvents = updatedEvents.filter { it.isFavorite }
                    val updatedSport = if (favoriteSports.contains(sport.sportId)) {
                        sport.copy(events = filteredEvents, isFavorite = true)
                    } else {
                        sport.copy(events = updatedEvents, isFavorite = false)
                    }
                    filteredSportsList.add(updatedSport)
                }
                _uiState.value = UiState.Success(filteredSportsList)
            }
        }
    }

    private fun filterEvents() {
        viewModelScope.launch(dispatcher) {
            useCase.fetchFavoriteEventListUseCase().collect { favoriteEvents ->
                val currentState = _uiState.value
                if (currentState is UiState.Success) {
                    val sportsList = currentState.result ?: emptyList()
                    val updatedSportsList = sportsList.map { sport ->
                        val updatedEvents = sport.events.map { event ->
                            event.copy(isFavorite = favoriteEvents.contains(event.eventId))
                        }
                        val filteredEvents = if (sport.isFavorite) {
                            updatedEvents.filter { it.isFavorite }
                        } else {
                            updatedEvents
                        }
                        sport.copy(events = filteredEvents)
                    }
                    _uiState.value = UiState.Success(updatedSportsList)
                }
            }
        }
    }

    private fun getErrorMessage(error: Throwable): Int {
        return when (error) {
            is SportsNotFoundException -> R.string.sports_not_found_error
            is IOException -> R.string.network_error
            else -> R.string.unexpected_error
        }
    }
}