package com.example.shoplive_problem.domain.usecase

import com.example.shoplive_problem.domain.base.BaseUseCase
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.repository.FavoriteRepository

class GetFavoriteListUseCase(
    private val favoriteRepository: FavoriteRepository
): BaseUseCase<Unit, List<Character>>() {
    override suspend fun execute(params: Unit): List<Character> {
        return favoriteRepository.getFavoriteList()
    }
}