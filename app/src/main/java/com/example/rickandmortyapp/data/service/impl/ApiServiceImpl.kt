package com.example.rickandmortyapp.data.service.impl

import android.content.Context
import com.example.rickandmortyapp.data.service.ApiService
import com.example.rickandmortyapp.domain.model.response.CharactersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import com.example.rickandmortyapp.domain.model.Result
import com.example.rickandmortyapp.domain.model.response.CharacterResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType.Application.Json
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer

class ApiServiceImpl(
    private val client: HttpClient,
    private val context: Context
) : ApiService
{
    override suspend fun getAllCharacters(): Result<List<CharacterResponse>>
    {
        return try{
            val httpResponse = client.get("/api/character/?page=2")
            println(httpResponse.bodyAsText())
            val response = httpResponse.body<CharactersResponse>()
            Result.Success(response.results)
        } catch(e: Exception){
            e.printStackTrace()
            println("########################################")
            Result.Error(e)
        }
    }
    override suspend fun getCharacterById(characterId: Long): Result<CharacterResponse>
    {
        return try{
            val httpResponse = client.get("$BASE_URL/character/$characterId")
            val response = httpResponse.body<CharactersResponse>()
            Result.Success(response.results.first())
        } catch(e: Exception){
            e.printStackTrace()
            Result.Error(e)
        }
    }
    companion object
    {
        private const val BASE_URL = "https://rickandmortyapi.com/api"
        private const val BASE_URL_PUBLIC = "$BASE_URL/public"
    }
}