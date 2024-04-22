package com.example.rickandmortyapp.database.mapper

import com.example.rickandmortyapp.database.entity.CharacterEntity
import com.example.rickandmortyapp.domain.model.response.CharacterResponse
import com.example.rickandmortyapp.domain.model.response.CharactersResponse
import com.example.rickandmortyapp.domain.model.response.Location
import com.example.rickandmortyapp.domain.model.response.Origin

class CharacterMapper
{
    fun asEntity(character: CharacterResponse): CharacterEntity{
        return CharacterEntity(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            type = character.type,
            gender = character.gender,
            origin = character.origin.toString(), //TODO()
            location = character.location.toString(), //TODO()
            image = character.image,
            episode = character.episode[0], //TODO(): create for list
            url = character.url,
            created = character.created,
        )
    }
    fun asResponse(character: CharacterEntity): CharacterResponse{
        return CharacterResponse(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            type = character.type,
            gender = character.gender,
            origin = Origin("RICK", "URL"), //TODO()
            location = Location("LOCATION", "URL"), //TODO()
            image = character.image,
            episode = listOf(character.episode), //TODO(): create for list
            url = character.url,
            created = character.created,
        )
    }
}