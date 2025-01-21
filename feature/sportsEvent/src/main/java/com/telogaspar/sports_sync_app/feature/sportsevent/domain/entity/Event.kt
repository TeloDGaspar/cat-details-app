package com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity

data class Event(
    val eventId: String,
    val sportID: String,
    val eventName: String,
    val eventStartTime: Long,
    val isFavorite: Boolean = false
)