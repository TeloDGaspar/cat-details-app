package com.telogaspar.sports_sync_app.feature.sportsevent.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.FetchSportsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsListViewModel @Inject constructor(
    private val fetchSportsListUseCase: FetchSportsListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _sportsList = MutableStateFlow<List<Sports>>(emptyList())
    val sportsList: StateFlow<List<Sports>> get() = _sportsList

    fun fetchSports() {
        viewModelScope.launch(dispatcher) {
            fetchSportsListUseCase().collect { sports ->
                _sportsList.value = sports
            }
        }
    }

}