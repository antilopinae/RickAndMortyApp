package com.example.rickandmortyapp.data.service

import com.example.rickandmortyapp.domain.model.response.CharactersResponse
import com.example.rickandmortyapp.domain.model.Result
import com.example.rickandmortyapp.domain.model.response.CharacterResponse

interface ApiService {
    suspend fun getAllCharacters() : Result<List<CharacterResponse>>
    suspend fun getCharacterById(characterId: Long): Result<CharacterResponse>
}