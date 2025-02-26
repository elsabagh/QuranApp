package com.example.quranapp.ui.surahDetails.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.quranapp.ui.surahDetails.SurahDetailsState
import com.example.quranapp.ui.surahDetails.SurahDetailsViewModel
import com.example.quranapp.util.formate.estimateAyahsPerPage
import com.example.quranapp.util.formate.formatAyahs

@Composable
fun AyahCard(
    state: SurahDetailsState,
    surahNumber: Int,
    viewModel: SurahDetailsViewModel,
    scrollState: ScrollState,
    availableHeight: Int,
    updateHeight: (Int) -> Unit,
    fontStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.fillMaxSize()
            .padding(horizontal = 16.dp)
            .onGloballyPositioned { coordinates ->
                val newHeight = coordinates.size.width
                if (newHeight != availableHeight) {
                    updateHeight(newHeight)
                    viewModel.updateAyahsPerPage(estimateAyahsPerPage(newHeight))
                }
            },
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            SelectionContainer {
                val displayedAyahs = viewModel.getCurrentPageAyahs()
                val formattedAyahs = formatAyahs(
                    surahNumber = surahNumber,
                    ayahs = displayedAyahs,
                    currentPage = state.currentPage,
                )
                Text(
                    text = formattedAyahs,
                    style = fontStyle, // Apply the font style
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .verticalScroll(scrollState)
                )
            }
        }
    }
}