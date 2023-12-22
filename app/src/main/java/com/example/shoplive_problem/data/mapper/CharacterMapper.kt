package com.example.shoplive_problem.data.mapper

import com.example.shoplive_problem.data.response.CharacterResponse
import com.example.shoplive_problem.domain.model.Character

object CharacterMapper {
    fun responseToData(response: CharacterResponse): List<Character>? {
        return response.data?.results?.map { result ->
            Character(
                id = result.id,
                name = result.name,
                description = result.description,
                thumbnailUrl = "${result.thumbnail?.path}.${result.thumbnail?.extension}"
            )
        }
    }
}