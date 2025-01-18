package com.telogaspar.sports_sync_app.feature.sportsevent.data.model

import com.google.gson.annotations.SerializedName

data class EventResponseItem(
    @SerializedName("i") val sportId: String? = null,      // Maps "i" to "id"
    @SerializedName("d") val sportName: String,    // Maps "d" to "description"
    @SerializedName("e") val events: List<Event>     // Maps "e" to "events"
)

data class Event(
    @SerializedName("i") val eventId: String,             // Maps "i" to "id"
    @SerializedName("si") val sportID: String, // Maps "si" to "sportIdentifier"
    @SerializedName("d") val eventName: String,    // Maps "d" to "description"
    @SerializedName("sh") val shortDescription: String? = null, // Optional "sh"
    @SerializedName("tt") val eventStartTime: Long        // Maps "tt" to "timestamp"
)