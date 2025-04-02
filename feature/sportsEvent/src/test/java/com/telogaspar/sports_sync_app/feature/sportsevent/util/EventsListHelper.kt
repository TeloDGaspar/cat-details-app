package com.telogaspar.sports_sync_app.feature.sportsevent.util

import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedsResponse
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.EventResponseItem
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.Image
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.Weight
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Breed
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event as EventEntity

object EventsListHelper {

    internal val mockedEventResponseItem = listOf(
        BreedsResponse(
            description = "Friendly and active breed.",
            id = "1",
            image = Image(height = 10, id = "1", width = 10, url = "https://example.com/image1.jpg"),
            life_span = "12-15 years",
            name = "Sample Breed",
            origin = "USA",
            temperament = "Loyal, Playful",
            weight = Weight(imperial = "10-20 lbs", metric = "4-9 kg")
        ),
        BreedsResponse(
            description = "Independent and intelligent breed.",
            id = "2",
            image = Image(height = 10, id = "1", width = 10, url = "https://example.com/image1.jpg"),
            life_span = "10-14 years",
            name = "Sample Breed 2",
            origin = "UK",
            temperament = "Smart, Curious",
            weight = Weight(imperial = "15-25 lbs", metric = "7-11 kg")
        )
    )

    internal val mockedSportsList = listOf(
        Breed(
            breedId = "cat",
            breedName = "Cat1",
            imageUrl = null,
            lifeSpan = null,
            origin = null,
            temperament = null,
            description = null,
            isFavorite = false
        ),
        Breed(
            breedId = "amer",
            breedName = "Cat2",
            imageUrl = null,
            lifeSpan = null,
            origin = null,
            temperament = null,
            description = null,
            isFavorite = false
        ),
    )

}