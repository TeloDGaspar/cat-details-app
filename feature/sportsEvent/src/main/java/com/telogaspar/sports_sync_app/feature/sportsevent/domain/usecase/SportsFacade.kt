package com.telogaspar.sports_sync_app.feature.sportsevent.domain.usecase

class SportsFacade(
    val fetchSportsListUseCase: FetchSportsListUseCase,
    val fetchFavoriteSportListUseCase: FetchFavoriteSportListUseCase,
    val fetchFavoriteEventListUseCase: FetchFavoriteEventListUseCase,
    val saveEventFavoriteUseCase: SaveEventFavoriteUseCase,
    val removeEventFavoriteUseCase: RemoveEventFavoriteUseCase,
    val saveSportFavoriteUseCase: SaveSportFavoriteUseCase,
    val removeSportFavoriteUseCase: RemoveSportFavoriteUseCase
)