package com.example.quranapp.ui.surahDetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quranapp.R
import com.example.quranapp.ui.surahDetails.component.AyahCard
import com.example.quranapp.ui.surahDetails.component.NavigationControls
import com.example.quranapp.ui.surahDetails.component.SurahTopBar
import com.example.quranapp.ui.theme.TypographyCustom
import com.example.quranapp.util.snackbar.SnackBarManager

@Composable
fun SurahDetailsScreen(
    surahNumber: Int,
    surahName: String,
    onBackClick: () -> Unit = {}
) {
    val viewModel: SurahDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    var availableHeight by remember { mutableStateOf(0) }
    var currentFontSize by remember { mutableStateOf(TypographyCustom.bodyMedium) }

    LaunchedEffect(surahNumber) {
        viewModel.getSurahAyahs(surahNumber)
    }

    LaunchedEffect(state.currentPage) {
        scrollState.scrollTo(0)
    }

    Scaffold(
        topBar = {
            SurahTopBar(
                surahName,
                onBackClick
            ) { newStyle ->
                currentFontSize = newStyle
            }
        }

    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                state.isLoading -> LoadingScreen()
                state.error != null -> ErrorScreen(state.error!!)
                else -> SurahContent(
                    state,
                    surahNumber,
                    viewModel,
                    scrollState,
                    availableHeight,
                    currentFontSize
                ) { newHeight -> availableHeight = newHeight }
            }
        }
    }
}


@Composable
fun SurahContent(
    state: SurahDetailsState,
    surahNumber: Int,
    viewModel: SurahDetailsViewModel,
    scrollState: ScrollState,
    availableHeight: Int,
    fontStyle: TextStyle,
    updateHeight: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BismillahText(fontStyle)
        AyahCard(
            state, surahNumber, viewModel,
            scrollState, availableHeight,
            updateHeight, fontStyle,
            modifier = Modifier.weight(0.8f),
        )
        NavigationControls(state, viewModel)
    }
}


@Composable
fun BismillahText(fontStyle: TextStyle) {
    Text(
        text = "بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ",
        style = fontStyle,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = error, color = Color.Red)
    }
    SnackBarManager.showMessage(R.string.no_internet_connection)

}
