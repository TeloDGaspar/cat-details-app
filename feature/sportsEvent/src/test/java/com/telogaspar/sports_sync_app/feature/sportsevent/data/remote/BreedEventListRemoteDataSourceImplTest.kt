package com.telogaspar.sports_sync_app.feature.sportsevent.data.remote

import com.telogaspar.sports_sync_app.feature.sportsevent.data.api.BreedsEventApi
import com.telogaspar.sports_sync_app.feature.sportsevent.util.EventsListHelper.mockedEventResponseItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class BreedEventListRemoteDataSourceImplTest{
    @MockK
    private lateinit var api: BreedsEventApi

    private lateinit var remoteDataSource: BreedEventListRemoteDataSourceImpl

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        remoteDataSource = BreedEventListRemoteDataSourceImpl(api)
    }

    @Test
    fun `fetchSportsEventList emits data from api`(): Unit = runTest{
        // Given
        coEvery { api.getBreeds() } returns mockedEventResponseItem

        // When
        val result = remoteDataSource.fetchBreedList()

        // Then
        assert(result == mockedEventResponseItem)
        coVerify { api.getBreeds() }
    }

}