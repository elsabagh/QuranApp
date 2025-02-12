package com.example.quranapp.ui.surahDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.example.quranapp.ui.surahDetails.component.AyahCard
import com.example.quranapp.ui.surahDetails.component.NavigationControls
import com.example.quranapp.ui.theme.TypographyCustom

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahTopBar(
    surahName: String,
    onBackClick: () -> Unit,
    onFontSizeChange: (TextStyle) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = surahName, modifier = Modifier.fillMaxWidth()) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Menu"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Small Font") },
                    onClick = {
                        onFontSizeChange(TypographyCustom.bodySmall)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Medium Font") },
                    onClick = {
                        onFontSizeChange(TypographyCustom.bodyMedium)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Large Font") },
                    onClick = {
                        onFontSizeChange(TypographyCustom.bodyLarge)
                        expanded = false
                    }
                )

            }
        }
    )
}

@Composable
fun SurahContent(
    state: SurahDetailsState,
    surahNumber: Int,
    viewModel: SurahDetailsViewModel,
    scrollState: androidx.compose.foundation.ScrollState,
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
}
