package com.example.shoplive_problem.domain.usecase

import com.example.shoplive_problem.domain.base.BaseUseCase
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.repository.FavoriteRepository

class AddFavoriteListUseCase(
    private val favoriteRepository: FavoriteRepository
) : BaseUseCase<Character, Boolean>() {
    override suspend fun execute(params: Character): Boolean {
        return favoriteRepository.addFavorite(params)
    }
}