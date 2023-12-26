package com.example.shoplive_problem.domain.usecase

import com.example.shoplive_problem.domain.base.BaseUseCase
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.repository.FavoriteRepository

class AddFavoriteListUseCase(
    private val favoriteRepository: FavoriteRepository
) : BaseUseCase<Character, List<Character>>() {
    override suspend fun execute(params: Character): List<Character> {
        val list = favoriteRepository.getFavoriteList()
        // favorite이 5개 이상이라면 오래된 것을 지우고
        if (list.size >= 5) {
            val isSuccess = favoriteRepository.deleteOldestFavorite()
        }
        // params를 add 후 성공 시 반영된 최신 리스트를 리턴, 그렇지 않다면 실패했다는 의미로 emptyList를 리턴
        val isSuccess = favoriteRepository.addFavorite(params)
        return if (isSuccess) {
            favoriteRepository.getFavoriteList()
        } else {
            emptyList()
        }
    }
}