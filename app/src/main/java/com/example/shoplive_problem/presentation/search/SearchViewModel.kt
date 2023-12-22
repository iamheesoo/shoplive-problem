package com.example.shoplive_problem.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplive_problem.data.network.ResultData
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getCharacterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    companion object {
        const val LIMIT = 10
    }

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>>
        get() = _characterList

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    var searchText: String = ""
        set(value) {
            field = value
            clearData()
        }
    var isPagingAvailable: Boolean = true

    private var job: Job? = null

    fun getSearchResult() {
        if (isPagingAvailable) {
            job = viewModelScope.launch {
                getCharacterListUseCase(
                    GetCharacterListUseCase.Params(
                        text = searchText,
                        limit = LIMIT,
                        offset = _characterList.value?.size ?: 0
                    )
                )
                    .collectLatest {
                        when (it) {
                            is ResultData.Success -> {
                                if (it.data?.isNotEmpty() == true) {
                                    _characterList.value = (_characterList.value ?: emptyList())
                                        .toMutableList().apply { addAll(it.data) }
                                } else {
                                    isPagingAvailable = false
                                }
                            }

                            is ResultData.Error -> {
                                _toastMessage.value = "서버 에러입니다.\n잠시 후 다시 시도해 주세요."
                                isPagingAvailable = false
                            }
                        }
                    }
            }
        }
    }

    private fun clearData() {
        // 검색어가 변경되면 화면에 노출되는 리스트, 페이징 변수, 진행 중이던 api 호출을 초기화한다
        isPagingAvailable = true
        _characterList.value = null
        job?.cancel()
    }
}