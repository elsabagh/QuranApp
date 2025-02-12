package com.example.quranapp.ui.home.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrayerTimeProgress(
    prayerName: String,
    hours: Int,
    minutes: Int,
    progress: Float, // Value from 0f to 1f
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(150.dp) // Adjust size as needed
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 15.dp.toPx()
            val startAngle = 135f
            val sweepAngle = 270f * progress

            // Background arc
            drawArc(
                color = Color.LightGray,
                startAngle = startAngle,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                color = Color.DarkGray,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = prayerName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = String.format("%02dh : %02dm", hours, minutes),
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPrayerTimeProgress() {
    PrayerTimeProgress(
        prayerName = "Fajr",
        hours = 4,
        minutes = 23,
        progress = 0.65f, // Example progress
        modifier = Modifier.padding(16.dp)
    )
}
