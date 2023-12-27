package com.example.shoplive_problem.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character_table ORDER BY timestamp ASC")
    fun getCharacterList(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(data: CharacterEntity): Long

    @Query("DELETE FROM character_table where id=:id")
    fun deleteCharacter(id: Int): Int

    @Query("DELETE FROM character_table WHERE timestamp = (SELECT MIN(timestamp) FROM character_table)")
    fun deleteOldestData(): Int

    @Query("UPDATE character_table SET name = :name, description = :description, thumbnailUrl = :thumbnailUrl WHERE id = :id")
    fun updateCharacter(id: Int, name: String, description: String, thumbnailUrl: String): Int
}