package com.example.rickandmortyapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyapp.database.entity.CharacterEntity

@Dao
interface CharacterDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(publication: CharacterEntity)

    @Delete
    fun delete(publication: CharacterEntity)

    @Query("SELECT * FROM CharacterEntity LIMIT :size OFFSET (:page * :size)")
    fun getAll(page: Int = 1, size: Int = 50): List<CharacterEntity>
}