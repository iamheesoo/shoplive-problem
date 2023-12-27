package com.example.shoplive_problem.domain.repository

import com.example.shoplive_problem.domain.model.Character

interface FavoriteRepository {
    // data를 favoriteDB에 추가
    suspend fun addFavorite(data: Character): Boolean

    // 삭제
    suspend fun deleteFavorite(id: Int): Boolean

    // CharacterEntity의 id, timestamp 외 데이터 갱신
    suspend fun updateFavorite(data: Character): Boolean

    // favoriteDB의 데이터 리스트 조회
    suspend fun getFavoriteList(): List<Character>

    // DB에서 가장 오래된 data 삭제
    suspend fun deleteOldestFavorite(): Boolean

    // favoriteList 리턴
    fun getRecentFavoriteList(): List<Character>

    // 데이터의 찜 여부
    fun isFavorite(id: Int): Boolean
}