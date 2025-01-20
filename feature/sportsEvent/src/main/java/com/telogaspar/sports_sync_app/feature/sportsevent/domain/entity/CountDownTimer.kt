package com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CountDownTimer(
    private val timeStamp: Long,
    private val updateInterval: Long = 1,
    private val currentTimeProvider: () -> Long = { System.currentTimeMillis() / 1000 }
) {
    private val _remainingTime = MutableStateFlow(
        timeStamp - currentTimeProvider()
    )
    val remainingTime: StateFlow<Long> = _remainingTime

    suspend fun start() {
        while (_remainingTime.value > 0) {
            delay(updateInterval * 1000L)
            _remainingTime.value -= updateInterval
        }
    }
}