package com.example.shoplive_problem.data.utils

import com.example.shoplive_problem.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest

object QueryUtils {
    fun getMarvelApiHash(timestamp: Long): String =
        md5("${timestamp}${BuildConfig.API_PRIVATE_KEY}${BuildConfig.API_PUBLIC_KEY}")

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}