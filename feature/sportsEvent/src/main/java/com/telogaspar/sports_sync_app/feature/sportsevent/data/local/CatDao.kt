package com.telogaspar.sports_sync_app.feature.sportsevent.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.telogaspar.sports_sync_app.feature.sportsevent.data.model.BreedDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    // Insert a new BreedDao object
    @Insert
    suspend fun insert(breed: BreedDao)

    // Insert a list of BreedDao objects
    @Insert
    suspend fun insertAll(breeds: List<BreedDao>)

    // Update an existing BreedDao object
    @Update
    suspend fun update(breed: BreedDao)

    // Get all sports from the sports_table
    @Query("SELECT * FROM breed_table")
    fun getAllSports(): Flow<List<BreedDao>>

    // Delete a BreedDao by breedId
    @Query("DELETE FROM breed_table WHERE breedId = :breedId")
    suspend fun delete(breedId: String)
}