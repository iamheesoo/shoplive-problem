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
        // favorite은 최대 5개까지 저장할 수 있다
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
    }

    fun isFavorite(id: Int): Boolean {
        return favoriteList.find { it.id == id } != null
    }

    // add, delete, update, get을 수행하여 favoriteList에 최신 DB 데이터를 조회해두면
    // view에서 데이터가 필요할 때 favoriteList를 리턴하여 DB 탐색 비용을 줄인다
    fun getRecentFavoriteList() = favoriteList
}