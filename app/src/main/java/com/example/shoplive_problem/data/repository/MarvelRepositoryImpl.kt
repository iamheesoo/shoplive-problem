package com.example.shoplive_problem.data.repository

import com.example.shoplive_problem.data.mapper.CharacterMapper
import com.example.shoplive_problem.data.network.ResultData
import com.example.shoplive_problem.datasource.MarvelRemoteDataSource
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.repository.MarvelRepository
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase

class MarvelRepositoryImpl(
    private val marvelRemoteDataSource: MarvelRemoteDataSource
) : MarvelRepository {
    override suspend fun getCharacterList(params: GetCharacterListUseCase.Params): ResultData<List<Character>?> {
        val result = marvelRemoteDataSource.getCharacterList(params)
        return if (result is ResultData.Success) {
            ResultData.Success(
                successData = result.data?.let { CharacterMapper.responseToData(it) }
                    ?: emptyList())
        } else {
            ResultData.Error(errorMessage = result.message)
        }
    }

}