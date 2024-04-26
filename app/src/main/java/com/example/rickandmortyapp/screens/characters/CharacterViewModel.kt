package com.example.rickandmortyapp.screens.characters

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.repository.CharacterRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.rickandmortyapp.domain.model.Result
import com.example.rickandmortyapp.domain.model.response.CharacterResponse
import com.example.rickandmortyapp.domain.model.response.CharactersResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.emptyFlow
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.properties.Delegates

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _characters = MutableStateFlow<List<CharacterResponse>>(emptyList())
    val characters: StateFlow<List<CharacterResponse>> = _characters

    private val _isLoadingData = MutableStateFlow(true)
    val isLoadingData: StateFlow<Boolean> = _isLoadingData

    private val _character = MutableStateFlow<CharacterResponse?>(null)
    val character: StateFlow<CharacterResponse?> = _character

    private val _error = MutableStateFlow<String>(String())

    private val page = AtomicLong(2)
    private var sizePages by Delegates.notNull<Long>()
    suspend fun loadCharacters() {
        _isLoading.value = true
        val data = repository.getAllCharacters(page.get())
        when(data){
            is Result.Success -> {
                _characters.emit((data as Result.Success<CharactersResponse>).data.results)
                _isLoading.value = false
                sizePages = (data as Result.Success<CharactersResponse>).data.info.pages
            }
            is Result.Error -> {
                (data as Result.Error<CharactersResponse>).e.printStackTrace()
            }
        }
    }

    suspend fun loadCharactersPrevPage() {
        if(page.get()>0){
            val data = repository.getAllCharacters(page.decrementAndGet())
            when(data){
                is Result.Success -> {
//                val prev_char = _characters.value.subList(0,5)
//                _characters.emit(prev_char+data.data)
                    _characters.emit((data as Result.Success<CharactersResponse>).data.results+_characters.value)
                }
                is Result.Error -> {
                    (data as Result.Error<CharactersResponse>).e.printStackTrace()
                }
            }
        }
    }

    suspend fun loadCharactersNextPage() {
        if(page.get()<sizePages){
            val data = repository.getAllCharacters(page.incrementAndGet())
            when(data){
                is Result.Success -> {
//                val prev_char = _characters.value.subList(_characters.value.size-5,_characters.value.size-1)
//                _characters.emit(prev_char+data.data)
                    _characters.emit(_characters.value+(data as Result.Success<CharactersResponse>).data.results)
                }
                is Result.Error -> {
                    (data as Result.Error<CharactersResponse>).e.printStackTrace()
                }
            }
        }
    }

    suspend fun loadCharacterData(characterId: Long)
    {
        _isLoadingData.value = true
        val data = repository.getCharacterById(characterId)
        when(data){
            is Result.Success -> {
                _character.emit((data as Result.Success<CharacterResponse>).data)
                _isLoadingData.value = false
            }
            is Result.Error -> {
                (data as Result.Error<CharacterResponse>).e.printStackTrace()
            }
        }
    }
}