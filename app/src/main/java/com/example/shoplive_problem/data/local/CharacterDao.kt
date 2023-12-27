package com.example.shoplive_problem.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM ${CharacterDatabase.CHARACTER_TABLE_NAME} ORDER BY timestamp ASC")
    fun getCharacterList(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(data: CharacterEntity): Long

    @Query("DELETE FROM ${CharacterDatabase.CHARACTER_TABLE_NAME} where id=:id")
    fun deleteCharacter(id: Int): Int

    @Query("DELETE FROM ${CharacterDatabase.CHARACTER_TABLE_NAME} WHERE timestamp = (SELECT MIN(timestamp) FROM ${CharacterDatabase.CHARACTER_TABLE_NAME}) AND rowid = (SELECT rowid FROM ${CharacterDatabase.CHARACTER_TABLE_NAME} WHERE timestamp = (SELECT MIN(timestamp) FROM ${CharacterDatabase.CHARACTER_TABLE_NAME}) ORDER BY rowid LIMIT 1)")
    fun deleteOldestData(): Int

    @Query("UPDATE ${CharacterDatabase.CHARACTER_TABLE_NAME} SET name = :name, description = :description, thumbnailUrl = :thumbnailUrl WHERE id = :id")
    fun updateCharacter(id: Int, name: String, description: String, thumbnailUrl: String): Int
}