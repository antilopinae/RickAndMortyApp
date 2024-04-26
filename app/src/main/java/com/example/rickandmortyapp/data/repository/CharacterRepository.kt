package com.example.rickandmortyapp.data.repository

import com.example.rickandmortyapp.data.service.ApiService
import com.example.rickandmortyapp.database.AppDatabase
import com.example.rickandmortyapp.domain.model.Result
import com.example.rickandmortyapp.domain.model.response.CharacterResponse

class CharacterRepository(
    private val apiService: ApiService,
    private val database: AppDatabase
)
{
    suspend fun getAllCharacters(page: Long) = apiService.getAllCharacters(page)
    suspend fun getCharacterById(characterId: Long) = apiService.getCharacterById(characterId)
}