package com.telogaspar.sports_sync_app.feature.sportsevent.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedDao
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Type
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class FavoriteListLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : FavoriteListLocalDataSource {

    companion object {
        private val FAVORITE_EVENTS_KEY = stringSetPreferencesKey("favorite_events")
        private val FAVORITE_SPORTS_KEY = stringSetPreferencesKey("favorite_sports")
    }

    override suspend fun saveFavorite(eventId: String, type: Type) {
        val typeKey = if (type == Type.FAVORITE_EVENTS) {
            FAVORITE_EVENTS_KEY
        } else {
            FAVORITE_SPORTS_KEY
        }
        dataStore.edit { preferences ->
            val currentFavorites = preferences[typeKey] ?: emptySet()
            preferences[typeKey] = currentFavorites + eventId
        }
    }

    override suspend fun removeFavorite(eventId: String, type: Type) {
        val typeKey = if (type == Type.FAVORITE_EVENTS) {
            FAVORITE_EVENTS_KEY
        } else {
            FAVORITE_SPORTS_KEY
        }
        dataStore.edit { preferences ->
            val currentFavorites = preferences[typeKey] ?: emptySet()
            preferences[typeKey] = currentFavorites - eventId
        }
    }

    override fun getFavorite(type: Type): Flow<Set<String>> {
        val typeKey = if (type == Type.FAVORITE_EVENTS) {
            FAVORITE_EVENTS_KEY
        } else {
            FAVORITE_SPORTS_KEY
        }
        return dataStore.data.map { preferences ->
            preferences[typeKey] ?: emptySet()
        }
    }
}

