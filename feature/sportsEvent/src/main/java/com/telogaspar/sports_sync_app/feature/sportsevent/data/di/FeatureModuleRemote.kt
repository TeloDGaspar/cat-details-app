package com.telogaspar.sports_sync_app.feature.sportsevent.data.di

import com.telogaspar.sports_sync_app.feature.sportsevent.data.api.SportsEventApi
import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.EventMapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.SportsEventListRemoteDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.SportsEventListRemoteDataSourceImpl
import com.telogaspar.sports_sync_app.feature.sportsevent.data.repository.SportListRepositoryImpl
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureModuleRemote {

    @Provides
    @Singleton
    fun provideFeatureApiService(retrofit: Retrofit): SportsEventApi {
        return retrofit.create(SportsEventApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSportsEventListRemoteDataSource(
        sportsEventApi: SportsEventApi
    ): SportsEventListRemoteDataSource = SportsEventListRemoteDataSourceImpl(sportsEventApi)


    @Provides
    @Singleton
    fun provideSportListRepository(
        remoteDataSource: SportsEventListRemoteDataSource
    ): SportListRepository = SportListRepositoryImpl(
        remoteDataSource = remoteDataSource,
        mapper = EventMapper()
    )
}