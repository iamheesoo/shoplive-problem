package com.example.shoplive_problem.data.datasource

import com.example.shoplive_problem.data.local.CharacterEntity

interface CharacterLocalDataSource {
    suspend fun getFavoriteList(): List<CharacterEntity>
    suspend fun addFavorite(entity: CharacterEntity): Boolean
    suspend fun deleteFavorite(id: Int): Boolean
}