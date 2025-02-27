package com.example.quranapp.ui.prayerTimes.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.quranapp.R
import com.example.quranapp.ui.theme.TypographyCustom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.prayer_times), style = TypographyCustom.bodyMedium)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
    )
}