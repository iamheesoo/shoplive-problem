package com.example.shoplive_problem.domain.usecase

import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.repository.FavoriteRepository

class FavoriteUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun getFavoriteList(): List<Character> {
        return favoriteRepository.getFavoriteList()
    }

    suspend fun addFavorite(data: Character): Boolean {
        // favorite은 최대 5개까지 저장할 수 있다
        val favoriteList = favoriteRepository.getRecentFavoriteList()
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

    suspend fun updateFavorite(data: Character) {
        favoriteRepository.updateFavorite(data)
        getFavoriteList()
    }

    fun isFavorite(id: Int): Boolean {
        return favoriteRepository.isFavorite(id)
    }

    fun getRecentFavoriteList(): List<Character> {
        return favoriteRepository.getRecentFavoriteList()
    }
}