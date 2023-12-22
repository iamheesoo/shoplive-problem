package com.example.shoplive_problem.domain.model

data class Character(
    val id: Int,
    val name: String = "",
    val description: String = "",
    val thumbnailUrl: String? = null
)