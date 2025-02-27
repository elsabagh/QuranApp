package com.example.quranapp.ui.quranList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quranapp.R
import com.example.quranapp.ui.quranList.component.QuranFilterBar
import com.example.quranapp.ui.quranList.component.SurahItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranListScreen(
    navController: NavController,
    onBackClick: () -> Unit = {}
) {
    val viewModel = hiltViewModel<QuranListViewModel>()
    val state by viewModel.state.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    // استخدم remember لتجنب إعادة الحساب غير الضرورية
    val filteredSurahs by remember(state.surahList, state.downloadedSurahNumbers, selectedFilter) {
        derivedStateOf {
            when (selectedFilter) {
                QuranFilterType.ALL -> state.surahList
                QuranFilterType.DOWNLOAD -> state.surahList.filter {
                    state.downloadedSurahNumbers.contains(it.number)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkDownloadedSurahs() // تحميل البيانات فقط عند الحاجة
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 8.dp)
        ) {
            // شريط الفلترة
            QuranFilterBar(selectedFilter = selectedFilter, onFilterSelected = viewModel::setFilter)


            when {
                state.isLoading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                state.error.isNotEmpty() -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.error, color = Color.Red)
                        if (state.showRetryButton) {
                            Button(onClick = { viewModel.reload() }) {
                                Text(stringResource(R.string.retry))
                            }
                        }
                    }
                }

                filteredSurahs.isEmpty() -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.no_surahs_found),
                            color = Color.Gray
                        )
                        if (state.showRetryButton) {
                            Button(onClick = { viewModel.reload() }) {
                                Text(stringResource(R.string.retry))
                            }
                        }
                    }
                }

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
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


