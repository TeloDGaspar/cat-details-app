package com.telogaspar.sports_sync_app.feature.sportsevent.data.model

import com.google.gson.annotations.SerializedName

data class EventResponseItem(
    @SerializedName("i") val sportId: String? = null,
    @SerializedName("d") val sportName: String,
    @SerializedName("e") val events: List<Event>
)
