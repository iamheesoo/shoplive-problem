package com.example.shoplive_problem.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    fun setLoadingVisible(isVisible: Boolean) {
        _isLoading.postValue(isVisible)
    }

    fun setToastMessage(message: String) {
        _toastMessage.postValue(message)
    }
}