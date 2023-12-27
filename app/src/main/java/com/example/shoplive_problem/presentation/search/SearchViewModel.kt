package com.example.shoplive_problem.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoplive_problem.data.network.ResultData
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.usecase.FavoriteUseCase
import com.example.shoplive_problem.domain.usecase.GetCharacterListUseCase
import com.example.shoplive_problem.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : BaseViewModel() {

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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase.getFavoriteList()
        }
    }

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
                        delay(500L)
                        when (it) {
                            is ResultData.Success -> {
                                if (it.data?.isNotEmpty() == true) {
                                    // isFavorite 반영
                                    val newList = it.data.map {
                                        it.copy(
                                            isFavorite = favoriteUseCase.isFavorite(it.id)
                                        )
                                    }
                                    // 최신 정보로 favoriteDB 갱신
                                    updateFavoriteDB(it.data)
                                    _characterList.value = (_characterList.value ?: emptyList())
                                        .toMutableList().apply { addAll(newList) }
                                } else {
                                    isPagingAvailable = false
                                }
                            }

                            is ResultData.Error -> {
                                _toastMessage.value = "서버 에러입니다.\n잠시 후 다시 시도해 주세요."
                                isPagingAvailable = false
                            }
                        }
                        setLoadingVisible(false)
                    }
            }
        }
    }

    fun addFavorite(data: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingVisible(true)
            favoriteUseCase.addFavorite(data)
                .let { isSuccess ->
                    if (isSuccess) {
                        _toastMessage.postValue("찜 추가하였습니다.")
                        updateCharacterListFavorite()
                    } else {
                        _toastMessage.postValue("찜 추가에 실패했습니다.\n잠시 후 다시 시도해 주세요.")
                    }
                }
            setLoadingVisible(false)
        }
    }

    fun deleteFavorite(data: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingVisible(true)
            favoriteUseCase.deleteFavorite(data.id)
                .let { isSuccess ->
                    if (isSuccess) {
                        _toastMessage.postValue("찜 삭제하였습니다.")
                        updateCharacterListFavorite()
                    } else {
                        _toastMessage.postValue("찜 삭제에 실패했습니다.\n잠시 후 다시 시도해 주세요.")
                    }
                }
            setLoadingVisible(false)
        }
    }

    fun updateCharacterListFavorite() {
        _characterList.postValue(
            _characterList.value?.map { it.copy(isFavorite = favoriteUseCase.isFavorite(it.id)) }
        )
    }

    private fun updateFavoriteDB(list: List<Character>) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteList = favoriteUseCase.getRecentFavoriteList()
            favoriteList.forEach { favoriteData ->
                list.find { it.id == favoriteData.id }?.let { compareData ->
                    // 정보가 변경되었다면
                    if (compareData.name != favoriteData.name || compareData.description != favoriteData.description
                        || compareData.thumbnailUrl != favoriteData.thumbnailUrl
                    ) {
                        favoriteUseCase.updateFavorite(compareData)
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
        setLoadingVisible(false)
    }
}