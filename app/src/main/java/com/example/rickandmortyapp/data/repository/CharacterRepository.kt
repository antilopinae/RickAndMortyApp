package com.example.rickandmortyapp.data.repository

import com.example.rickandmortyapp.data.service.ApiService

class CharacterRepository(private val apiService: ApiService) {
    suspend fun getAllCharacters() = apiService.getAllCharacters()
}