package com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper

import com.telogaspar.core.mapper.Mapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedsResponse
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.EventResponseItem
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Breed
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Event
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale


internal class EventMapper : Mapper<List<BreedsResponse>, List<Breed>> {

    override fun map(source: List<BreedsResponse>): List<Breed> {
        return source.map { item ->
            Breed(
                breedId = item.id,
                breedName = item.name,
                description = item.description,
                temperament = item.temperament,
                lifeSpan = item.life_span,
                origin = item.origin,
                imageUrl = item.image?.url ?: ""
            )
        }
    }
}
