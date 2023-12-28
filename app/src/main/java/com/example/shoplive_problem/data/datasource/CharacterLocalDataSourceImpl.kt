package com.example.shoplive_problem.data.datasource

import com.example.shoplive_problem.data.local.CharacterDao
import com.example.shoplive_problem.data.local.CharacterDatabase
import com.example.shoplive_problem.data.local.CharacterEntity

class CharacterLocalDataSourceImpl(
    private val characterDao: CharacterDao
) : CharacterLocalDataSource {

    override suspend fun getFavoriteList(): List<CharacterEntity> {
        return characterDao.getCharacterList()
    }

    override suspend fun addFavorite(entity: CharacterEntity): Boolean {
        return characterDao.insertCharacter(entity) != -1L
    }

    override suspend fun deleteFavorite(id: Int): Boolean {
        return characterDao.deleteCharacter(id) == 1
    }

    override suspend fun deleteOldestFavorite(): Boolean {
        return characterDao.deleteOldestData() == 1
    }

    override suspend fun updateFavorite(
        id: Int,
        name: String,
        description: String,
        thumbnailUrl: String
    ): Boolean {
        return characterDao.updateCharacter(
            id = id,
            name = name,
            description = description,
            thumbnailUrl = thumbnailUrl
        ) > 0
    }
}