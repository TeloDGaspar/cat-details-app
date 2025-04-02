package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telogaspar.core.R
import com.telogaspar.core.presentation.viewModel.UiState
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Breed
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.exception.SportsNotFoundException
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.BreedFacade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(
    private val useCase: BreedFacade, private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Breed>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Breed>>> = _uiState.asStateFlow()

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

    fun fetchBreedByFavorite(): List<Breed> {
        return (uiState.value as? UiState.Success)?.result?.filter { it.isFavorite } ?: emptyList()
    }

    private fun handleSportsSuccess(sports: List<Breed>) {
        filterFavoriteSports(sports)
    }

    private fun handleError(error: Throwable) {
        _uiState.value = UiState.Error(getErrorMessage(error))
    }


    fun updateSport(sport: Breed) {
        viewModelScope.launch(dispatcher) {
            val isFavorite = !sport.isFavorite
            if (isFavorite) {
                useCase.saveSportFavoriteUseCase(sport.breedId)
            } else {
                useCase.removeSportFavoriteUseCase(sport.breedId)
            }
            (uiState.value as? UiState.Success)?.result?.let { filterFavoriteSports(it) }
        }
    }

    private fun filterFavoriteSports(sports: List<Breed>) {
        viewModelScope.launch(dispatcher) {
            useCase.fetchFavoriteSportListUseCase()
                .collect { favoriteSports ->
                    val updatedSports = sports.map { sport ->
                        sport.copy(isFavorite = favoriteSports.contains(sport.breedId))
                    }
                    _uiState.value = UiState.Success(updatedSports)
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