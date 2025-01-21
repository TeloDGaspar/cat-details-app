package com.telogaspar.sports_sync_app.feature.sportsevent.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.favoritesDataStore by preferencesDataStore(name = "favorites")