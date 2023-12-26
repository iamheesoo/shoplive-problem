package com.example.shoplive_problem.domain.usecase

import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.repository.FavoriteRepository

class FavoriteUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    private var favoriteList = listOf<Character>()
    suspend fun getFavoriteList(): List<Character> {
        val list = favoriteRepository.getFavoriteList()
        favoriteList = list
        return favoriteList
    }

    suspend fun addFavorite(data: Character): Boolean {
        val isDeleteSuccess = if (favoriteList.size >= 5) {
            favoriteRepository.deleteOldestFavorite()
        } else {
            true
        }
        val isSuccess = if (isDeleteSuccess) {
            favoriteRepository.addFavorite(data)
        } else {
            false
        }
        getFavoriteList()
        return isSuccess
    }

    suspend fun deleteFavorite(id: Int): Boolean {
        val isSuccess = favoriteRepository.deleteFavorite(id)
        getFavoriteList()
        return isSuccess
    }

    fun isFavorite(id: Int): Boolean {
        return favoriteList.find { it.id == id } != null
    }
}