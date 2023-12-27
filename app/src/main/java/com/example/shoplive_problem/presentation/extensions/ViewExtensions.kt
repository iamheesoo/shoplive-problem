package com.example.shoplive_problem.presentation.extensions

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

fun View.throttleOnClick(interval: Long = 500L, onSafeClick: (View) -> Unit) {
    setOnClickListener(
        object : View.OnClickListener {
            private var lastTimeClicked: Long = 0

            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastTimeClicked > interval) {
                    lastTimeClicked = SystemClock.elapsedRealtime()
                    onSafeClick(v)
                }
            }
        }
    )
}

fun View.hideKeyboard() {
    clearFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}