package com.telogaspar.sports_sync_app.feature.sportsevent.domain.di

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.FetchFavoriteEventListUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.FetchFavoriteSportListUseCase
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
    ): SportsFacade {
        return SportsFacade(
            fetchSportsListUseCase = FetchSportsListUseCase(repositorySports),
            fetchFavoriteSportListUseCase = FetchFavoriteSportListUseCase(repositorySports),
            fetchFavoriteEventListUseCase = FetchFavoriteEventListUseCase(repositorySports),
            saveEventFavoriteUseCase = SaveEventFavoriteUseCase(repositorySports),
            removeEventFavoriteUseCase = RemoveEventFavoriteUseCase(repositorySports),
            saveSportFavoriteUseCase = SaveSportFavoriteUseCase(repositorySports),
            removeSportFavoriteUseCase = RemoveSportFavoriteUseCase(repositorySports)
        )
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}