package com.example.shoplive_problem.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.usecase.FavoriteUseCase
import com.example.shoplive_problem.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteUseCase: FavoriteUseCase
) : BaseViewModel() {

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>>
        get() = _characterList

    init {
        getFavoriteList()
    }

    private fun getFavoriteList() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingVisible(true)
            favoriteUseCase.getFavoriteList()
                .let {
                    _characterList.postValue(it)
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

    fun getRecentFavoriteList() = favoriteUseCase.getRecentFavoriteList()

    private fun updateCharacterListFavorite() {
        _characterList.postValue(
            _characterList.value?.filter { favoriteUseCase.isFavorite(it.id) }
                ?.map { it.copy(isFavorite = true) }
        )
    }
}