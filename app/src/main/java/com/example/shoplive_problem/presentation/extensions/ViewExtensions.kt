package com.example.shoplive_problem.presentation.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

fun View.click(call: () -> Unit) {
    this.post {
        val scope = findViewTreeLifecycleOwner()?.lifecycleScope
        if (scope == null) {
            val activity = this.context as? AppCompatActivity
            activity?.let {
                this.clicks().throttleFirst(1000L).onEach {
                    call.invoke()
                }.launchIn(it.lifecycleScope)
            }
        } else {
            this.clicks().throttleFirst(1000L).onEach {
                call.invoke()
            }.launchIn(scope)
        }
    }
}

fun View.hideKeyboard() {
    clearFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}