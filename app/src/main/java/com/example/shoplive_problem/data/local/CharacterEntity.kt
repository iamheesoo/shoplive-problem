package com.example.shoplive_problem.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoplive_problem.domain.model.Character

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var thumbnailUrl: String? = null,
    var timestamp: Long = 0L
)

fun Character.toCharacterEntity() =
    CharacterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        timestamp = System.currentTimeMillis()
    )

// DB -> presentation 사용 용도
fun CharacterEntity.toCharacter() =
    Character(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        isFavorite = true
    )