package com.example.rickandmortyapp.screens.characters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.rickandmortyapp.domain.model.response.CharacterResponse
import com.example.rickandmortyapp.screens.theme.RickAndMortyAppTheme
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.absoluteValue

class CharacterDetailActivity: ComponentActivity()
{
    private val viewModel: CharacterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val characterNumber: Long = intent.getLongExtra("character", 0)

        lifecycleScope.launch {
            viewModel.loadCharacterData(characterNumber)
        }
        setContent {
            RickAndMortyAppTheme {
                CharacterDetailScreen()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CharacterDetailScreen(viewModel: CharacterViewModel = koinViewModel()) {
        val character by viewModel.character.collectAsState()
        val isLoading by viewModel.isLoadingData.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Characters detail") },
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
                    CharacterInfo(character!!)
                }
            }
        }
    }


    @Composable
    private fun CharacterInfo(character: CharacterResponse)
    {
        Card(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = character.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(text = "Gender: ${character.gender}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Status: ${character.status}", style = MaterialTheme.typography.titleMedium)
                Text(text = "In series: ${character.episode.size}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Location: ${character.location.name}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Species: ${character.species}", style = MaterialTheme.typography.titleMedium)

                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = character.image,
                    contentDescription = "Image of ${character.name}"
                )
            }
        }
    }
}