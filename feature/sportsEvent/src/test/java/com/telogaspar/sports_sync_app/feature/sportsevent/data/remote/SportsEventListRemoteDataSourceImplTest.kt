package com.telogaspar.sports_sync_app.feature.sportsevent.data.remote

import com.telogaspar.sports_sync_app.feature.sportsevent.data.api.SportsEventApi
import com.telogaspar.sports_sync_app.feature.sportsevent.util.EventsListHelper.mockedEventResponseItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class SportsEventListRemoteDataSourceImplTest{
    @MockK
    private lateinit var api: SportsEventApi

    private lateinit var remoteDataSource: SportsEventListRemoteDataSourceImpl

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        remoteDataSource = SportsEventListRemoteDataSourceImpl(api)
    }

    @Test
    fun `fetchSportsEventList emits data from api`(): Unit = runTest{
        // Given
        coEvery { api.getEvents() } returns mockedEventResponseItem

        // When
        val result = remoteDataSource.fetchSportsEventList()

        // Then
        assert(result == mockedEventResponseItem)
        coVerify { api.getEvents() }
    }

}