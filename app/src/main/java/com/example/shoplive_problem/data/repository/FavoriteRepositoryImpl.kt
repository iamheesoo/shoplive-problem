package com.example.shoplive_problem.data.repository

import com.example.shoplive_problem.data.datasource.CharacterLocalDataSource
import com.example.shoplive_problem.data.local.toCharacter
import com.example.shoplive_problem.data.local.toCharacterEntity
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val characterLocalDataSource: CharacterLocalDataSource
) : FavoriteRepository {
    override suspend fun addFavorite(data: Character): Boolean {
        return characterLocalDataSource.addFavorite(data.toCharacterEntity())
    }

    override suspend fun deleteFavorite(id: Int): Boolean {
        return characterLocalDataSource.deleteFavorite(id)
    }

    override suspend fun getFavoriteList(): List<Character> {
        val result = characterLocalDataSource.getFavoriteList()
        return result.map { it.toCharacter() }
    }
}