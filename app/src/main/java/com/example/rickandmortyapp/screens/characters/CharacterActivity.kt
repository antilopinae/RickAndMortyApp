package com.example.rickandmortyapp.screens.characters

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.rickandmortyapp.domain.model.response.CharacterResponse
import com.example.rickandmortyapp.domain.model.response.CharactersResponse
import com.example.rickandmortyapp.screens.theme.RickAndMortyAppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.rickandmortyapp.domain.model.Result
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList

class CharacterActivity: ComponentActivity() {
    private val viewModel: CharacterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSubscribe()
        setContent {
            RickAndMortyAppTheme {
                CharacterListScreen()
            }
        }
    }
    private fun initSubscribe() {
        lifecycleScope.launch {
            if(viewModel.characters.firstOrNull() != null) return@launch
            viewModel.characters.collect{ characters ->
                val intent = Intent(this@CharacterActivity, CharacterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CharacterListScreen(viewModel: CharacterViewModel = koinViewModel()) {
        val isLoading by viewModel.isLoading.collectAsState()
        val characters by viewModel.characters.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Rick and Morty characters") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    CharacterList(characters)
                }
            }
        }
    }

    @Composable
    fun CharacterList(characters: List<CharacterResponse>) {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(characters) { item ->
                CharacterItem(item)
            }
        }
    }

    @Composable
    fun CharacterItem(character: CharacterResponse) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                AsyncImage(
                    model = character.image,
                    contentDescription = "Image of ${character.name}"
                )
                Column {
                    Text(text = character.name, style = MaterialTheme.typography.headlineSmall)
                    Text(text = character.status, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}