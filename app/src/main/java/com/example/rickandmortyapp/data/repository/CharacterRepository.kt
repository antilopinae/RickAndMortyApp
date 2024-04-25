package com.example.rickandmortyapp.data.repository

import com.example.rickandmortyapp.data.service.ApiService
import com.example.rickandmortyapp.database.AppDatabase

class CharacterRepository(
    private val apiService: ApiService,
    private val database: AppDatabase
)
{
    suspend fun getAllCharacters() = apiService.getAllCharacters()
}