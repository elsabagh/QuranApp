package com.example.quranapp.ui.prayerTimes.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.quranapp.ui.theme.TypographyCustom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Prayer Times", style = TypographyCustom.bodyMedium)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
    )
}