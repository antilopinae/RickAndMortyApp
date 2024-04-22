package com.example.rickandmortyapp.domain.models

import com.google.gson.annotations.SerializedName

data class CharactersResponseFull(
    val info: Info,
    val results: List<CharactersResponse>,
)

data class Info(
    val count: Long,
    val pages: Long,
    val next: String?,
    val prev: String?,
)

data class CharactersResponse(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
)

data class Origin(
    val name: String,
    val url: String,
)

data class Location(
    val name: String,
    val url: String,
)

enum class CharacterStatus(val statusName: String) {
    @SerializedName("Dead")
    DEAD("Dead"),

    @SerializedName("Alive")
    ALIVE("Alive"),

    @SerializedName("unknown")
    UNKNOWN("Unknown"),
}
