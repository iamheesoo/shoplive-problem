package com.example.shoplive_problem.domain.repository

import com.example.shoplive_problem.domain.model.Character

interface FavoriteRepository {
    suspend fun addFavorite(data: Character): Boolean

    suspend fun deleteFavorite(id: Int): Boolean

    suspend fun getFavoriteList(): List<Character>

    suspend fun deleteOldestFavorite(): Boolean
}