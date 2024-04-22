package com.example.rickandmortyapp

import android.app.Application
import com.example.rickandmortyapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickAndMorty: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RickAndMorty)
            modules(appModule)
        }
    }
}