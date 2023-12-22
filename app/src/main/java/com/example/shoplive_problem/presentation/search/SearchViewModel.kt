package com.example.shoplive_problem.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplive_problem.data.network.ResultData
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>>
        get() = _characterList

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    fun getSearchResult(text: String) {
        viewModelScope.launch {
            getCharacterListUseCase(
                GetCharacterListUseCase.Params(
                    text = text,
                    limit = 10,
                    offset = _characterList.value?.size ?: 0
                )
            )
                .collectLatest {
                    when (it) {
                        is ResultData.Success -> {
                            _characterList.value = it.data
                        }

                        is ResultData.Error -> {
                            _toastMessage.value = "서버 에러입니다.\n잠시 후 다시 시도해 주세요."
                        }
                    }
                }

        }
    }
}