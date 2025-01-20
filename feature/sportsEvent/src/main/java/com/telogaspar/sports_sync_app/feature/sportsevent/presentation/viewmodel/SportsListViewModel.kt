package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel

import android.util.Log
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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SportsListViewModel @Inject constructor(
    private val useCase: SportsFacade, private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Sports>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Sports>>> = _uiState

    private val _listSports: ArrayList<Sports> = arrayListOf()

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
        _listSports.addAll(sports)
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

            useCase.fetchFavoriteEventListUseCase().collect { event ->
                _uiState.update { uiState ->
                    if (uiState is UiState.Success) {
                        val sportList : ArrayList<Sports> = arrayListOf()
                        val updatedSportsList = uiState.result?.map { sport ->
                            val eventsList : ArrayList<Event> = arrayListOf()
                            sport.events.forEach { itemEvent ->
                                if (event.contains(itemEvent.eventId)) {
                                    eventsList.add(itemEvent.copy(isFavorite = isFavorite))
                                } else {
                                    eventsList.add(itemEvent)
                                }
                            }
                            sportList.add(sport.copy(events = eventsList))
                            sport
                        }
                        Log.i("UpdateEvent", "Updated Sports List: $updatedSportsList")
                        UiState.Success(updatedSportsList)
                    } else {
                        uiState
                    }
                }
            }

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
            useCase.fetchFavoriteSportListUseCase().collect { sports ->
                val filterList: ArrayList<Sports> = arrayListOf()
                _listSports.forEach { sport ->
                    if (sports.contains(sport.sportId)) {
                        val test = sport.events.filter { it.isFavorite }
                        val sportEvensFilter = sport.copy(events = test, isFavorite = true)
                        filterList.add(sportEvensFilter)
                    } else {

                        filterList.add(sport.copy(isFavorite = false))
                    }
                }
                _uiState.value = UiState.Success(filterList)
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
