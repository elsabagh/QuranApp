package com.example.quranapp.ui.quranList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quranapp.R
import com.example.quranapp.ui.quranList.component.QuranFilterBar
import com.example.quranapp.ui.quranList.component.SurahItem
import com.example.quranapp.util.snackbar.SnackBarManager

@Composable
fun QuranListScreen(
    navController: NavController,
    onBackClick: () -> Unit = {}
) {
    val viewModel = hiltViewModel<QuranListViewModel>()
    val state by viewModel.state.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    // Refresh downloaded Surahs when entering the screen
    LaunchedEffect(Unit) {
        viewModel.checkDownloadedSurahs()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp)
    ) {
        // Back Button
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = {
                    onBackClick()
                    viewModel.checkDownloadedSurahs() // Refresh when leaving
                }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }


        // Filter Bar
        QuranFilterBar(selectedFilter = selectedFilter, onFilterSelected = viewModel::setFilter)

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error.isNotEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.error)
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    // Filtered Surah List
                    val filteredSurahs = when (selectedFilter) {
                        QuranFilterType.ALL -> state.surahList
                        QuranFilterType.DOWNLOAD -> state.surahList.filter {
                            state.downloadedSurahNumbers.contains(it.number)
                        }

                    }

                    if (filteredSurahs.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "No Internet. Please check your connection.",
                                    color = Color.Gray
                                )
                                Button(onClick = { viewModel.reload() }) {
                                    Text("Retry")
                                }
                            }
                        }

                        SnackBarManager.showMessage(R.string.check_your_internet_connection)

                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
                        ) {
                            items(filteredSurahs.size) { index ->
                                val surah = filteredSurahs[index]
                                SurahItem(
                                    surah = surah,
                                    onClick = {
                                        navController.navigate("surahDetails/${surah.number}/${surah.name}")
                                    },
                                    onDownloadClick = {
                                        viewModel.downloadSurah(surah.number)
                                    },
                                    isDownloading = state.downloadingSurahNumber == surah.number,
                                    isDownloaded = state.downloadedSurahNumbers.contains(surah.number)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}
