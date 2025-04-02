package com.telogaspar.sports_sync_app.feature.sportsevent.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.telogaspar.sports_sync_app.feature.sportsevent.data.api.BreedsEventApi
import com.telogaspar.sports_sync_app.feature.sportsevent.data.local.CatDao
import com.telogaspar.sports_sync_app.feature.sportsevent.data.local.CatDatabase
import com.telogaspar.sports_sync_app.feature.sportsevent.data.local.FavoriteListLocalDataSourceImpl
import com.telogaspar.sports_sync_app.feature.sportsevent.data.local.favoritesDataStore
import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.EventMapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.BreedEventListRemoteDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.BreedEventListRemoteDataSourceImpl
import com.telogaspar.sports_sync_app.feature.sportsevent.data.repository.SportListRepositoryImpl
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.BreedListLocalDataBase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.BreedListLocalDataBaseImpl
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
    fun provideCatDatabase(@ApplicationContext context: Context): CatDatabase {
        return Room.databaseBuilder(context, CatDatabase::class.java, "cat_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCatDao(catDatabase: CatDatabase): CatDao {
        return catDatabase.breedDao()
    }

    @Provides
    @Singleton
    fun provideBreedListLocalDatabase(catDao: CatDao): BreedListLocalDataBase {
        return BreedListLocalDataBaseImpl(catDao)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.favoritesDataStore
    }

    @Provides
    @Singleton
    internal fun providePreferencesRepository(
        dataStore: DataStore<Preferences>
    ): FavoriteListLocalDataSource {
        return FavoriteListLocalDataSourceImpl(dataStore)
    }

    @Provides
    @Singleton
    internal fun provideFeatureApiService(retrofit: Retrofit): BreedsEventApi {
        return retrofit.create(BreedsEventApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideSportsEventListRemoteDataSource(
        breedsEventApi: BreedsEventApi
    ): BreedEventListRemoteDataSource {
        return BreedEventListRemoteDataSourceImpl(breedsEventApi)
    }

    @Provides
    @Singleton
    internal fun provideSportListRepository(
        remoteDataSource: BreedEventListRemoteDataSource,
        dataStore: DataStore<Preferences>,
        breedListLocalDataBase: BreedListLocalDataBase
    ): SportListRepository {
        return SportListRepositoryImpl(
            remoteDataSource = remoteDataSource,
            mapper = EventMapper(),
            localDataSource = FavoriteListLocalDataSourceImpl(dataStore),
            breedListLocalDataBase = breedListLocalDataBase
        )
    }
}