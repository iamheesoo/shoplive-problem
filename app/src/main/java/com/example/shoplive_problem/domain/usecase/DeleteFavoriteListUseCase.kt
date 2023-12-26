package com.example.shoplive_problem.domain.usecase

import com.example.shoplive_problem.domain.base.BaseUseCase
import com.example.shoplive_problem.domain.repository.FavoriteRepository

class DeleteFavoriteListUseCase(
    private val favoriteRepository: FavoriteRepository
) : BaseUseCase<Int, Boolean>() {
    override suspend fun execute(params: Int): Boolean {
        return favoriteRepository.deleteFavorite(params)
    }
}