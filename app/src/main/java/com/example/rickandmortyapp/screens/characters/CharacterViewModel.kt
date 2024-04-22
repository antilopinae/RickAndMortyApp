package com.example.rickandmortyapp.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.repository.CharacterRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.rickandmortyapp.domain.model.Result
import com.example.rickandmortyapp.domain.model.response.CharacterResponse

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _characters = MutableStateFlow<List<CharacterResponse>>(emptyList())
    val characters: StateFlow<List<CharacterResponse>> = _characters

    private val _error = MutableStateFlow<String>(String())
    val error: StateFlow<String> = _error

    init {
//        loadCharacters()
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
            when(data){
                is Result.Success -> {
                    _characters.emit(data.data)
                }
                is Result.Error -> {

                }
            }
        }
    }
}