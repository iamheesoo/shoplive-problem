package com.example.shoplive_problem.data.datasource

import com.example.shoplive_problem.data.network.ResultData
import com.example.shoplive_problem.data.response.CharacterResponse
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase

interface MarvelRemoteDataSource {
    suspend fun getCharacterList(
        params: GetCharacterListUseCase.Params
    ): ResultData<CharacterResponse>
}