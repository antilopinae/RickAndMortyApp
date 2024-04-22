package com.example.rickandmortyapp.domain.model

sealed class Result<T>
{
    data class Success<T>(val data: T): Result<T>()
    data class Error<T>(val e: Exception): Result<T>()
}