package com.example.rickandmortyapp.di

import androidx.room.Room
import com.example.rickandmortyapp.data.repository.CharacterRepository
import com.example.rickandmortyapp.data.service.ApiService
import com.example.rickandmortyapp.data.service.impl.ApiServiceImpl
import com.example.rickandmortyapp.database.AppDatabase
import com.example.rickandmortyapp.screens.characters.CharacterViewModel
import com.google.gson.JsonSerializationContext
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import okhttp3.OkHttp
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
//    single {
//        Retrofit.Builder()
//            .baseUrl("https://rickandmortyapi.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiServiceImpl::class.java)
//    }
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            defaultRequest {
                host = "rickandmortyapi.com"
                url{
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }
    single { ApiServiceImpl(get(), get()) } bind ApiService::class
}

val viewModelModule = module {
    viewModel{ CharacterViewModel(get()) }
}

val repositoryModule = module {
    single { CharacterRepository(get(), get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "RickAndMortyDatabase")
            .build()
    }
}


val appModule = module {

} + networkModule + viewModelModule + repositoryModule + databaseModule