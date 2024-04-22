package com.example.rickandmortyapp.di

import com.example.rickandmortyapp.data.api.ApiService
import com.example.rickandmortyapp.data.api.ApiServiceImpl
import com.example.rickandmortyapp.data.repositories.CharacterRepository
import com.example.rickandmortyapp.ui.characters.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    scope<ApiService> { ApiServiceImpl()  }
    single { CharacterRepository(get()) }

    viewModel { CharacterViewModel(get()) }
}

