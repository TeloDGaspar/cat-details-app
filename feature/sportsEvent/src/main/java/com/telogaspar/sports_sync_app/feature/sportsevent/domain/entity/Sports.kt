package com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity

data class Sports(
    val sportId: String,
    val sportName: String,
    val events: List<Event>
)

data class Event(
    val eventId: String,
    val sportID: String,
    val eventName: String,
    val eventStartTime: Long
)