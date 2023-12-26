package com.example.shoplive_problem.data.datasource

import com.example.shoplive_problem.data.local.CharacterDatabase
import com.example.shoplive_problem.data.local.CharacterEntity

class CharacterLocalDataSourceImpl(
    private val database: CharacterDatabase
): CharacterLocalDataSource {
    private val characterDao = database.characterDao()

    override suspend fun getFavoriteList(): List<CharacterEntity> {
        return characterDao.getCharacterList()
    }

    override suspend fun addFavorite(entity: CharacterEntity): Boolean {
        return characterDao.insertCharacter(entity) != -1L
    }

    override suspend fun deleteFavorite(id: Int): Boolean {
        return characterDao.deleteCharacter(id) == 1
    }
}