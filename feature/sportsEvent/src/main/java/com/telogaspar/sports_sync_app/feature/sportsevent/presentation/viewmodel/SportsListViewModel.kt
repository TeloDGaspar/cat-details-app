package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telogaspar.core.presentation.UiState
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.FetchSportsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Date
import javax.inject.Inject
import com.telogaspar.core.R
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.exception.SportsNotFoundException

@HiltViewModel
class SportsListViewModel @Inject constructor(
    private val fetchSportsListUseCase: FetchSportsListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _sportsList = MutableStateFlow<List<Sports>>(emptyList())
    val sportsList: StateFlow<List<Sports>> get() = _sportsList

    private val _uiState = MutableStateFlow<UiState<List<Sports>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Sports>>> = _uiState


    fun fetchSports() {
        viewModelScope.launch(dispatcher) {
            fetchSportsListUseCase().onStart {
                _uiState.value = UiState.Loading
            }.catch { error ->
                handleError(error)
            }.collect { sports ->
                handleSportsSuccess(sports)
            }
        }
    }

    private fun handleSportsSuccess(sports: List<Sports>){
        _uiState.value = UiState.Success(sports)
    }

    private fun handleError(error: Throwable) {
        _uiState.value = UiState.Error(getErrorMessage(error))
    }

    /*fun startCountdown(event: List<Event>) {
        val currentTimeUnix = System.currentTimeMillis() / 1000
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch {
            event.forEach { event ->
                var timeLeft = event.eventStartTime - currentTimeUnix
                while (timeLeft > 0) {
                    // Update the remaining time every second
                    _eventTimers.value += (event.eventId to timeLeft)

                    // Wait for 1 second
                    delay(1000L)

                    // Decrease the time by 1 second
                    timeLeft--
                }
            }


            // Once the countdown finishes
            _remainingTime.value = 0L
        }
    }*/

    private fun Long.formatUnixTimeToHHMMSS(): String {
        val hours = (this / 3600) % 24
        val minutes = (this % 3600) / 60
        val seconds = this % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun updateEvent(event: Event) {
        _uiState.update { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    val updatedSportsList = uiState.result?.map { sport ->
                        sport.copy(events = sport.events.map { existingEvent ->
                            if (existingEvent.eventId == event.eventId) {
                                existingEvent.copy(isFavorite = event.isFavorite)
                            } else {
                                existingEvent
                            }
                        })
                    }
                    UiState.Success(updatedSportsList).also {
                        Log.i("Devlog", "UiState updated: $it")
                    }
                }
                else -> uiState
            }
        }
        Log.i("Devlog", "Event updated: ${_uiState.value}")
    }

    private fun getErrorMessage(error: Throwable): Int {
        return when (error) {
            is SportsNotFoundException -> R.string.sports_not_found_error
            is IOException -> R.string.network_error
            else -> R.string.unexpected_error
        }
    }

}