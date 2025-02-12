package com.example.quranapp.ui.surahDetails.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranapp.ui.surahDetails.SurahDetailsState
import com.example.quranapp.ui.surahDetails.SurahDetailsViewModel

@Composable
fun NavigationControls(state: SurahDetailsState, viewModel: SurahDetailsViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(
            onClick = { viewModel.goToNextPage() },
            enabled = state.currentPage < state.totalPages - 1
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Next")
        }
        Text(
            text = "${state.currentPage + 1} / ${state.totalPages}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = { viewModel.goToPreviousPage() },
            enabled = state.currentPage > 0
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Previous")
        }
    }
}