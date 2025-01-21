package com.telogaspar.sports_sync_app.feature.sportsevent.data.model

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("i") val eventId: String,
    @SerializedName("si") val sportID: String,
    @SerializedName("d") val eventName: String,
    @SerializedName("sh") val shortDescription: String? = null,
    @SerializedName("tt") val eventStartTime: Long
)