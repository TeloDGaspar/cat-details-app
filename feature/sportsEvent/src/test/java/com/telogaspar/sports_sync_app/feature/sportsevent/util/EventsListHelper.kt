package com.telogaspar.sports_sync_app.feature.sportsevent.util

import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.EventResponseItem
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event as EventEntity

object EventsListHelper {

    internal val mockedEventResponseItem = listOf(
        EventResponseItem(
            events = listOf(
                Event(
                    eventId = "1",
                    sportID = "1",
                    eventName = "Sample Event",
                    shortDescription = "Sample Description",
                    eventStartTime = 1633024800L
                )
            ),
            sportName = "Sample Sport",
            sportId = "1"
        ),
        EventResponseItem(
            events = listOf(
                Event(
                    eventId = "12",
                    sportID = "12",
                    eventName = "Sample Event2",
                    shortDescription = "Sample2 Description",
                    eventStartTime = 1633025800L
                )
            ),
            sportName = "Sample2 Sport",
            sportId = "2"
        )
    )

    internal val mockedSportsList = listOf(
        Sports(
            events = listOf(
                EventEntity (
                    eventId = "1",
                    sportID = "1",
                    eventName = "Sample Event",
                    eventStartTime = 1633024800L
                )
            ),
            sportName = "Sample Sport",
            sportId = "1"
        ),
        Sports(
            events = listOf(
                EventEntity (
                    eventId = "12",
                    sportID = "12",
                    eventName = "Sample Event2",
                    eventStartTime = 1633025800L
                )
            ),
            sportName = "Sample2 Sport",
            sportId = "2"
        )
    )

}