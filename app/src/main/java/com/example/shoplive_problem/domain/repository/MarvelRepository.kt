package com.example.shoplive_problem.domain.repository

import com.example.shoplive_problem.data.network.ResultData
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase

interface MarvelRepository {
    // api 통신으로 데이터 획득
    suspend fun getCharacterList(params: GetCharacterListUseCase.Params): ResultData<List<Character>?>
}