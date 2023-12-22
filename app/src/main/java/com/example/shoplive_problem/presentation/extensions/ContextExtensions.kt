package com.example.shoplive_problem.presentation.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.TypedValue
import android.widget.Toast
import kotlin.math.roundToInt

fun Context.getDpToPx(dp: Float): Int {
    val resultPix =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    return resultPix.roundToInt()
}

fun Context.isConnectNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(ConnectivityManager::class.java)
    val capabilities =
        connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)

    capabilities?.let { _capabilities ->
        if (_capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            || _capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || _capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            || _capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
        )
            return true
    }

    return false
}

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}