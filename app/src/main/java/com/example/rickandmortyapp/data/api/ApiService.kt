package com.example.rickandmortyapp.data.api

import com.example.rickandmortyapp.domain.models.CharactersResponseFull
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    suspend fun getAllCharacters() : CharactersResponseFull
}