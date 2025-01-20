package com.telogaspar.sports_sync_app.feature.sportsevent.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.telogaspar.sports_sync_app.feature.sportsevent.data.api.SportsEventApi
import com.telogaspar.sports_sync_app.feature.sportsevent.data.local.FavoriteListLocalDataSourceImpl
import com.telogaspar.sports_sync_app.feature.sportsevent.data.local.favoritesDataStore
import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.EventMapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.SportsEventListRemoteDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.SportsEventListRemoteDataSourceImpl
import com.telogaspar.sports_sync_app.feature.sportsevent.data.repository.SportListRepositoryImpl
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureModuleRemote {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.favoritesDataStore
    }

    @Provides
    @Singleton
    internal fun providePreferencesRepository(dataStore: DataStore<Preferences>): FavoriteListLocalDataSource {
        return FavoriteListLocalDataSourceImpl(dataStore)
    }

    @Provides
    @Singleton
    internal fun provideFeatureApiService(retrofit: Retrofit): SportsEventApi {
        return retrofit.create(SportsEventApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideSportsEventListRemoteDataSource(
        sportsEventApi: SportsEventApi
    ): SportsEventListRemoteDataSource = SportsEventListRemoteDataSourceImpl(sportsEventApi)


    @Provides
    @Singleton
    internal fun provideSportListRepository(
        remoteDataSource: SportsEventListRemoteDataSource, dataStore: DataStore<Preferences>
    ): SportListRepository = SportListRepositoryImpl(
        remoteDataSource = remoteDataSource,
        mapper = EventMapper(),
        localDataSource = FavoriteListLocalDataSourceImpl(dataStore)
    )
}