package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

class BreedFacade(
    val fetchSportsListUseCase: FetchSportsListUseCase,
    val fetchFavoriteSportListUseCase: FetchFavoriteSportListUseCase,
    val saveSportFavoriteUseCase: SaveSportFavoriteUseCase,
    val removeSportFavoriteUseCase: RemoveSportFavoriteUseCase
)