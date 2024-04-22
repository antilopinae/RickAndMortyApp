package com.example.rickandmortyapp.di

import androidx.room.Room
import com.example.rickandmortyapp.data.service.ApiService
import com.example.rickandmortyapp.data.service.impl.ApiServiceImpl
import com.example.rickandmortyapp.data.repository.CharacterRepository
import com.example.rickandmortyapp.database.AppDatabase
import com.example.rickandmortyapp.screens.characters.CharacterViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
//    single {
//        Retrofit.Builder()
//            .baseUrl("https://rickandmortyapi.com/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
//    }
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }
    single { ApiServiceImpl(get(), get()) } bind ApiService::class
}

val viewModelModule = module {
    viewModel{ CharacterViewModel(get())}
}

val repositoryModule = module {
    single { CharacterRepository(get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "RickAndMortyDatabase")
            .build()
    }
}


val appModule = module {

} + networkModule + viewModelModule + repositoryModule + databaseModule