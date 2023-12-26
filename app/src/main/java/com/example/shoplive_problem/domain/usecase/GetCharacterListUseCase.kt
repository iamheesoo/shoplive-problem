package com.example.shoplive_problem.domain.usecase

import com.example.shoplive_problem.data.network.ResultData
import com.example.shoplive_problem.domain.base.BaseUseCase
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.repository.MarvelRepository

class GetCharacterListUseCase(
    private val marvelRepository: MarvelRepository
): BaseUseCase<GetCharacterListUseCase.Params, ResultData<List<Character>?>>() {

    override suspend fun execute(params: Params): ResultData<List<Character>?> {
        return marvelRepository.getCharacterList(params)
    }

    data class Params(
        val text: String,
        val limit: Int,
        val offset: Int
    )
}