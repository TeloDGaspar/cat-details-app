package com.telogaspar.sports_sync_app.feature.sportsevent.domain.di

import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.FetchFavoriteSportListUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.FetchSportsListUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.RemoveSportFavoriteUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.SaveSportFavoriteUseCase
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase.BreedFacade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideUseCases(
        repositorySports: SportListRepository,
    ): BreedFacade {
        return BreedFacade(
            fetchSportsListUseCase = FetchSportsListUseCase(repositorySports),
            fetchFavoriteSportListUseCase = FetchFavoriteSportListUseCase(repositorySports),
            saveSportFavoriteUseCase = SaveSportFavoriteUseCase(repositorySports),
            removeSportFavoriteUseCase = RemoveSportFavoriteUseCase(repositorySports)
        )
    }
}