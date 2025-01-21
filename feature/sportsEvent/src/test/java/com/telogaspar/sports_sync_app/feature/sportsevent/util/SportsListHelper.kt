package com.telogaspar.sports_sync_app.feature.sportsevent.util

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports

object SportsListTestHelper {

    internal val mockedSportsListViewModel = listOf(
        Sports(
            "sport1", "Football", listOf(
                Event("event1", "sport1", "Match1", 1234567890, false),
                Event("event2", "sport1", "Match2", 1234567891, false)
            ), false
        ), Sports(
            "sport2", "Basketball", listOf(
                Event("event3", "sport2", "Game1", 1234567892, false),
                Event("event4", "sport2", "Game2", 1234567893, false)
            ), false
        )
    )
}