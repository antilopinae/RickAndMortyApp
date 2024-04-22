package com.example.rickandmortyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortyapp.database.dao.CharacterDao
import com.example.rickandmortyapp.database.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase()
{
    abstract fun getApplicationDao(): CharacterDao
}