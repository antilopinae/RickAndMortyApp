package com.example.rickandmortyapp.ui.characters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import coil.compose.AsyncImage
import com.example.rickandmortyapp.domain.models.CharactersResponse
import com.example.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import org.koin.androidx.compose.koinViewModel

class CharacterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppTheme {
                CharacterListScreen()
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
    fun CharacterList(characters: List<CharactersResponse>) {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(characters) { item ->
                CharacterItem(item)
            }
        }
    }

    @Composable
    fun CharacterItem(character: CharactersResponse) {
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