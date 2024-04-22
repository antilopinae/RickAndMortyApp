package com.example.rickandmortyapp.ui.characters

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.repositories.CharacterRepository
import com.example.rickandmortyapp.domain.models.CharactersResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _characters = MutableStateFlow<List<CharactersResponse>>(emptyList())
    val characters: StateFlow<List<CharactersResponse>> = _characters

    private val _error = MutableStateFlow<String>(String())
    val error: StateFlow<String> = _error

    init {
        loadCharacters()
    }

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _isLoading.value = false
        _characters.value = emptyList()
        _error.value = throwable.localizedMessage!!
    }

    private fun loadCharacters() {
        viewModelScope.launch(exceptionHandler) {
            _isLoading.value = true
            val data = repository.getAllCharacters()
            _characters.value = data.results
        }
    }
}