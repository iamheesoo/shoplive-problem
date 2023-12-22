package com.example.shoplive_problem.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    @SerialName("code")
    val code: Int = 0,
    @SerialName("status")
    val status: String = "",
    @SerialName("data")
    val data: Data? = null,
){
    fun isSuccess() = this.code == 200
}

@Serializable
data class Data(
    @SerialName("offset")
    val offset: Int = 0,
    @SerialName("limit")
    val limit: Int = 0,
    @SerialName("total")
    val total: Int = 0,
    @SerialName("count")
    val count: Int = 0,
    @SerialName("results")
    val results: List<Result> = emptyList()
)

@Serializable
data class Result(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("modified")
    val modified: String = "",
    @SerialName("thumbnail")
    val thumbnail: Thumbnail? = null,
    @SerialName("resourceURI")
    val resourceURI: String = "",
    @SerialName("comics")
    val comics: Collection? = null,
    @SerialName("series")
    val series: Collection? = null,
    @SerialName("stories")
    val stories: Collection? = null,
    @SerialName("events")
    val events: Collection? = null,
    @SerialName("urls")
    val urls: List<Url> = emptyList()
)

@Serializable
data class Thumbnail(
    @SerialName("path")
    val path: String = "",
    @SerialName("extension")
    val extension: String = ""
)

@Serializable
data class Collection(
    @SerialName("available")
    val available: Int = 0,
    @SerialName("collectionURI")
    val collectionURI: String = "",
    @SerialName("items")
    val items: List<Item> = emptyList(),
    @SerialName("returned")
    val returned: Int = 0,
)

@Serializable
data class Item(
    @SerialName("resourceURI")
    val resourceURI: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("type")
    val type: String = ""
)

@Serializable
data class Url(
    @SerialName("type")
    val type: String = "",
    @SerialName("url")
    val url: String = ""
)
