package com.example.rickandmortyapp.data.service

import com.example.rickandmortyapp.domain.model.response.CharactersResponse
import com.example.rickandmortyapp.domain.model.Result
import com.example.rickandmortyapp.domain.model.response.CharacterResponse

interface ApiService {
    suspend fun getAllCharacters(page: Long) : Result<CharactersResponse>
    suspend fun getCharacterById(characterId: Long): Result<CharacterResponse>
}