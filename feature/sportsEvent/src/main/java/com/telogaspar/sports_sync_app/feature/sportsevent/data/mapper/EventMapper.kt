package com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper

import com.telogaspar.core.mapper.Mapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.EventResponseItem
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale


internal class EventMapper : Mapper<List<EventResponseItem>, List<Sports>> {

    override fun map(source: List<EventResponseItem>): List<Sports> {
        return source.map { item ->
            Sports(
                sportId = item.sportId ?: "",
                sportName = item.sportName,
                events = item.events.map { event ->
                    Event(
                        eventId = event.eventId,
                        sportID = event.sportID,
                        eventName = event.eventName,
                        eventStartTime = event.eventStartTime
                    )
                }
            )
        }
    }
}
