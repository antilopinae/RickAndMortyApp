package com.example.rickandmortyapp.screens.characters

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.internal.wait
import java.util.concurrent.atomic.AtomicBoolean

class CharacterListActivity: ComponentActivity() {
    private val viewModel: CharacterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.loadCharacters()
        }
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

    var lastItem: Int =0

    @Composable
    fun CharacterList(characters: List<CharacterResponse>) {
        val listState = LazyListState()

        LaunchedEffect(key1 = viewModel) {
            listState.scrollToItem(listState.layoutInfo.totalItemsCount)
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(characters) { item ->
                CharacterItem(item)
            }
            val totalItems = listState.layoutInfo.totalItemsCount
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()

            if (lastVisibleItem != null && lastVisibleItem.index == totalItems - 3) {
                onScrollNearEnd()
                lastItem = lastVisibleItem.index
            }
            if (lastVisibleItem != null && lastVisibleItem.index == 3)
            {
                onScrollNearUp()
                lastItem = lastVisibleItem.index
            }
        }
    }

    @Composable
    fun CharacterItem(character: CharacterResponse) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    OnItemCLick(character = character)
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = "Image of ${character.name}",
                    modifier = Modifier.fillMaxHeight()
                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Gender: ${character.gender}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Status: ${character.status}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "In series: ${character.episode.size}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }

    var start1 = AtomicBoolean(true)
    var start2 = AtomicBoolean(true)

    private fun onScrollNearEnd()
    {
        if(start1.get()){

            val cor1 = lifecycleScope.launch {
                viewModel.loadCharactersNextPage()
            }
            start1.set(false)
            lifecycleScope.launch {
                cor1.join()
                start1.set(true)
            }
        }
    }

    private fun onScrollNearUp()
    {
        if(start2.get()){
            val cor2 = lifecycleScope.launch {
                viewModel.loadCharactersPrevPage()
            }
            start2.set(false)

            lifecycleScope.launch {
                cor2.join()
                start1.set(true)
            }
        }
    }

    private fun OnItemCLick(character: CharacterResponse){
        println("You clicked on ${character.name}")

        val intent = Intent(this, CharacterDetailActivity::class.java)

        intent.putExtra("character", character.id)

        startActivity(intent)
    }
}

