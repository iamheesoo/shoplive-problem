package com.example.shoplive_problem.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.domain.usecase.AddFavoriteListUseCase
import com.example.shoplive_problem.domain.usecase.DeleteFavoriteListUseCase
import com.example.shoplive_problem.domain.usecase.GetFavoriteListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BookmarkViewModel(
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val addFavoriteListUseCase: AddFavoriteListUseCase,
    private val deleteFavoriteListUseCase: DeleteFavoriteListUseCase
) : BaseViewModel() {
    private val _favoriteList = MutableLiveData<List<Character>>()
    val favoriteList: LiveData<List<Character>>
        get() = _favoriteList

    init {
        getFavoriteList()
    }

    private fun getFavoriteList() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingVisible(true)
            getFavoriteListUseCase(Unit)
                .let {
                    setLoadingVisible(false)
                    _favoriteList.postValue(it)
                }
        }
    }

    fun addFavorite(data: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingVisible(true)
            addFavoriteListUseCase(data)
                .let { list ->
                    setLoadingVisible(false)
                    if (list.isNotEmpty()) {
                        _toastMessage.postValue("찜 추가하였습니다.")
                        _favoriteList.postValue(list)
                    } else {
                        _toastMessage.postValue("찜 추가에 실패했습니다.\n잠시 후 다시 시도해 주세요.")
                    }
                }
        }
    }

    fun deleteFavorite(data: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingVisible(true)
            deleteFavoriteListUseCase(data.id)
                .let { isSuccess ->
                    setLoadingVisible(false)
                    if (isSuccess) {
                        _toastMessage.postValue("찜 삭제하였습니다.")
                        // data를 계속 copy하므로 remove를 사용한 삭제는 주소값이 달라 사용 불가능
                        _favoriteList.postValue(
                            _favoriteList.value?.toMutableList()
                                ?.apply { removeIf { it.id == data.id } }
                        )
                    } else {
                        _toastMessage.postValue("찜 삭제에 실패했습니다.\n잠시 후 다시 시도해 주세요.")
                    }
                }
        }
    }

    fun updateFavoriteList(list: List<Character>) {
        // favoriteList = list가 아니라 addAll하는 이유는
        // 'hulk' 검색결과의 찜 리스트와 'iron' 검색결과의 찜 리스트가 모두 favoriteList에 있어야
        // Favorite 탭에서 두 결과를 모두 노출할 수 있음
        val newFavoriteList = _favoriteList.value?.toMutableList() ?: mutableListOf()
        newFavoriteList.addAll(list)
        _favoriteList.value = newFavoriteList
    }

    fun isFavoriteCharacter(data: Character): Boolean {
        return _favoriteList.value?.find { it.id == data.id } != null
    }

}