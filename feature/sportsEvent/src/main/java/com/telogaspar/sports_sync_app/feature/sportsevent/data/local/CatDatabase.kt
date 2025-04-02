package com.telogaspar.sports_sync_app.feature.sportsevent.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedDao

@Database(entities = [BreedDao::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {

    abstract fun breedDao(): CatDao
}