package com.example.rickandmortyapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmortyapp.domain.model.response.Location
import com.example.rickandmortyapp.domain.model.response.Origin

@Entity
class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "species")
    var species: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "gender")
    var gender: String,

    @ColumnInfo(name = "origin") //Origin
    var origin: String,

    @ColumnInfo(name = "location") //Location
    var location: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "episode") //list<String>
    var episode: String,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "created")
    var created: String,
)