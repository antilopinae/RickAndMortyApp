package com.example.rickandmortyapp.data.api

import com.example.rickandmortyapp.domain.models.CharactersResponse
import com.example.rickandmortyapp.domain.models.CharactersResponseFull
import com.example.rickandmortyapp.domain.models.Info
import com.example.rickandmortyapp.domain.models.Location
import com.example.rickandmortyapp.domain.models.Origin

class ApiServiceImpl : ApiService {
    override suspend fun getAllCharacters(): CharactersResponseFull {
        return CharactersResponseFull(
            Info(
                (111).toLong(),
                (111).toLong(),
                (111).toString(),
                (111).toString(),
            ),
            listOf(CharactersResponse(
                (111).toLong(),
                (111).toString(),
                (111).toString(),
                (111).toString(),
                (111).toString(),
                (111).toString(),
                Origin("Rick", "url"),
                Location("World", "url"),
                (111).toString(),
                listOf((111).toString()),
                (111).toString(),
                (111).toString(),
            ))
        )
    }
}