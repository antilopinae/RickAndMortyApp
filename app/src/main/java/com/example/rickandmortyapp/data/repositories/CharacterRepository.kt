package com.example.rickandmortyapp.data.repositories

import com.example.rickandmortyapp.data.api.ApiService

class CharacterRepository(private val apiService: ApiService) {
    suspend fun getAllCharacters() = apiService.getAllCharacters()
}