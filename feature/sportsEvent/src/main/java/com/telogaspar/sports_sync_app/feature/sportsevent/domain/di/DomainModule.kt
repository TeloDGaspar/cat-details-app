package com.telogaspar.sports_sync_app.feature.sportsevent.domain.di

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.FetchSportsListUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.RemoveEventFavoriteUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.RemoveSportFavoriteUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.SaveEventFavoriteUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.SaveSportFavoriteUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.SportsFacade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideUseCases(
        repositorySports: SportListRepository,
        repositoryPreferences: FavoriteListLocalDataSource
    ): SportsFacade {
        return SportsFacade(
            fetchSportsListUseCase = FetchSportsListUseCase(repositorySports),
            saveEventFavoriteUseCase = SaveEventFavoriteUseCase(repositoryPreferences),
            removeEventFavoriteUseCase = RemoveEventFavoriteUseCase(repositoryPreferences),
            saveSportFavoriteUseCase = SaveSportFavoriteUseCase(repositoryPreferences),
            removeSportFavoriteUseCase = RemoveSportFavoriteUseCase(repositoryPreferences)
        )
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}