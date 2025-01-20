package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.CountDownTimer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountDownTimerTest {

    @Test
    fun `timer starts with correct initial value`() = runTest {
        val initialTime = 10L
        val fixedTime = 100L
        val timer = CountDownTimer(
            timeStamp = initialTime + fixedTime,
            currentTimeProvider = { fixedTime }
        )

        val remainingTime = timer.remainingTime.first()
        assertEquals(initialTime, remainingTime)
    }

    @Test
    fun `timer decrements over time`() = runTest {
        val initialTime = 5L
        val updateInterval = 1L
        val fixedTime = 100L
        val timer = CountDownTimer(
            timeStamp = initialTime + fixedTime,
            updateInterval = updateInterval,
            currentTimeProvider = { fixedTime }
        )

        val collectedTimes = mutableListOf<Long>()
        val job = launch {
            timer.remainingTime.take(initialTime.toInt() + 1).toList(collectedTimes)
        }

        launch { timer.start() }
        advanceUntilIdle()

        assertEquals(listOf(5L, 4L, 3L, 2L, 1L, 0L), collectedTimes)
        job.cancel()
    }

    @Test
    fun `timer stops at zero`() = runTest {
        val initialTime = 3L
        val updateInterval = 1L
        val fixedTime = 100L
        val timer = CountDownTimer(
            timeStamp = initialTime + fixedTime,
            updateInterval = updateInterval,
            currentTimeProvider = { fixedTime }
        )

        launch { timer.start() }
        advanceUntilIdle()

        val remainingTime = timer.remainingTime.first()
        assertEquals(0L, remainingTime)
    }

    @Test
    fun `timer updates at specified intervals`() = runTest {
        val initialTime = 10L
        val updateInterval = 2L
        val fixedTime = 100L
        val timer = CountDownTimer(
            timeStamp = initialTime + fixedTime,
            updateInterval = updateInterval,
            currentTimeProvider = { fixedTime }
        )

        val collectedTimes = mutableListOf<Long>()
        val job = launch {
            timer.remainingTime.take(6).toList(collectedTimes)
        }

        launch { timer.start() }
        advanceTimeBy(updateInterval * 1000L * 3)
        advanceUntilIdle()

        assertEquals(listOf(10L, 8L, 6L, 4L, 2L, 0L), collectedTimes)
        job.cancel()
    }
}
