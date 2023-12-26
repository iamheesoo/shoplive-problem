package com.example.shoplive_problem.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoplive_problem.data.network.ResultData
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.usecase.AddFavoriteListUseCase
import com.example.shoplive_problem.domain.usecase.DeleteFavoriteListUseCase
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase
import com.example.shoplive_problem.domain.usecase.GetFavoriteListUseCase
import com.example.shoplive_problem.presentation.BookmarkViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val addFavoriteListUseCase: AddFavoriteListUseCase,
    private val deleteFavoriteListUseCase: DeleteFavoriteListUseCase
) : BookmarkViewModel(getFavoriteListUseCase, addFavoriteListUseCase, deleteFavoriteListUseCase) {

    companion object {
        const val LIMIT = 10
    }

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>>
        get() = _characterList

    var searchText: String = ""
        set(value) {
            field = value
            clearData()
        }
    var isPagingAvailable: Boolean = true

    private var job: Job? = null

    fun getSearchResult() {
        if (isPagingAvailable) {
            // 페이징 처리 시 진행 중이던 flow는 종료한다
            // ex. scroll down으로 페이징 요청하여 진행중에 다시 scroll down하는 경우
            job?.cancel()
            job = viewModelScope.launch {
                setLoadingVisible(true)
                getCharacterListUseCase(
                    GetCharacterListUseCase.Params(
                        text = searchText,
                        limit = LIMIT,
                        offset = _characterList.value?.size ?: 0
                    )
                )
                    .let {
                        // todo 다시체크
                        delay(500L)
                        setLoadingVisible(false)
                        when (it) {
                            is ResultData.Success -> {
                                if (it.data?.isNotEmpty() == true) {
                                    // isFavorite 반영
                                    val newList = it.data.map {
                                        it.copy(
                                            isFavorite = isFavoriteCharacter(it)
                                        )
                                    }
                                    _characterList.value = (_characterList.value ?: emptyList())
                                        .toMutableList().apply { addAll(newList) }
                                    // 검색결과 list에서 favorite만 뽑아 BookmarkViewModel에 저장
                                    val favoriteList = newList.filter { it.isFavorite }
                                    if (favoriteList.isNotEmpty()) {
                                        updateFavoriteList(favoriteList)
                                    }
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

    fun updateCharacterListFavorite() {
        _characterList.value =
            _characterList.value?.map { it.copy(isFavorite = isFavoriteCharacter(it)) }
    }


    private fun clearData() {
        // 검색어가 변경되면 화면에 노출되는 리스트, 페이징 변수, 진행 중이던 api 호출을 초기화한다
        isPagingAvailable = true
        _characterList.value = null
        job?.cancel()
    }
}