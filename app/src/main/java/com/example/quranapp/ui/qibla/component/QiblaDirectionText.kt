package com.example.quranapp.ui.qibla.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun QiblaDirectionText(qiblaDirection: Int) {
    Text(
        text = "Qibla: $qiblaDirection°",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewQiblaDirectionText() {
    QiblaDirectionText(qiblaDirection = 45)
}

