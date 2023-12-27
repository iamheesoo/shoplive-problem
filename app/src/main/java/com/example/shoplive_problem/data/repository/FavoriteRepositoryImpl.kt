package com.example.shoplive_problem.data.repository

import com.example.shoplive_problem.data.datasource.CharacterLocalDataSource
import com.example.shoplive_problem.data.local.toCharacter
import com.example.shoplive_problem.data.local.toCharacterEntity
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val characterLocalDataSource: CharacterLocalDataSource
) : FavoriteRepository {

    private var favoriteList = listOf<Character>()

    override suspend fun addFavorite(data: Character): Boolean {
        return characterLocalDataSource.addFavorite(data.toCharacterEntity())
    }

    override suspend fun deleteFavorite(id: Int): Boolean {
        return characterLocalDataSource.deleteFavorite(id)
    }

    override suspend fun updateFavorite(data: Character): Boolean {
        return characterLocalDataSource.updateFavorite(
            id = data.id,
            name = data.name,
            description = data.description,
            thumbnailUrl = data.thumbnailUrl ?: ""
        )
    }

    override suspend fun getFavoriteList(): List<Character> {
        val result = characterLocalDataSource.getFavoriteList()
        val list =  result.map { it.toCharacter() }
        favoriteList = list
        return list
    }

    override suspend fun deleteOldestFavorite(): Boolean {
        return characterLocalDataSource.deleteOldestFavorite()
    }

    override fun getRecentFavoriteList(): List<Character> {
        return favoriteList
    }

    override fun isFavorite(id: Int): Boolean {
        return favoriteList.find { it.id == id } != null
    }
}