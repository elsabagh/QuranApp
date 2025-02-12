package com.example.quranapp.ui.home.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quranapp.data.model.Timings
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NextPrayerTime(
    prayerTimings: Timings,
    modifier: Modifier = Modifier,
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    // Update time every second
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L) // 1 second delay
            currentTime = LocalTime.now()
        }
    }

    val prayerTimesList = listOf(
        "Fajr" to LocalTime.parse(prayerTimings.Fajr, formatter),
        "Dhuhr" to LocalTime.parse(prayerTimings.Dhuhr, formatter),
        "Asr" to LocalTime.parse(prayerTimings.Asr, formatter),
        "Maghrib" to LocalTime.parse(prayerTimings.Maghrib, formatter),
        "Isha" to LocalTime.parse(prayerTimings.Isha, formatter)
    )

    // Find the next and previous prayer
    val nextPrayer = prayerTimesList.firstOrNull { it.second.isAfter(currentTime) }
        ?: prayerTimesList.first() // Fallback to Fajr if past Isha

    val previousPrayer = prayerTimesList.lastOrNull { it.second.isBefore(currentTime) }
        ?: prayerTimesList.last() // Fallback to Isha if before Fajr

    val nextPrayerTime = nextPrayer.second
    val previousPrayerTime = previousPrayer.second

    val timeLeft = Duration.between(currentTime, nextPrayerTime).seconds
    val totalDuration = Duration.between(previousPrayerTime, nextPrayerTime).seconds

    // Handle cases when next prayer is after midnight
    val adjustedTotalDuration = if (totalDuration < 0) totalDuration + 24 * 3600 else totalDuration
    val adjustedTimeLeft = if (timeLeft < 0) timeLeft + 24 * 3600 else timeLeft

    val progress = 1f - (adjustedTimeLeft.toFloat() / adjustedTotalDuration.toFloat())

    val hours = adjustedTimeLeft / 3600
    val minutes = (adjustedTimeLeft % 3600) / 60

    PrayerTimeProgress(
        prayerName = nextPrayer.first,
        hours = hours.toInt(),
        minutes = minutes.toInt(),
        progress = progress.coerceIn(0f, 1f), // Ensure valid range
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NextPrayerTimePreview() {
    NextPrayerTime(
        prayerTimings = Timings(
            Fajr = "05:00",
            Dhuhr = "12:00",
            Asr = "17:30",
            Maghrib = "18:00",
            Isha = "19:00"
        )
    )
}
