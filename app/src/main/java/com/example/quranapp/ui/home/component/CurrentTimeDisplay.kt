package com.example.quranapp.ui.home.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.quranapp.ui.theme.TypographyCustom
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentTimeDisplay(modifier: Modifier = Modifier) {
    
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L) // Update every second
            currentTime = LocalTime.now()
        }
    }

    val formattedTime = remember(currentTime) {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a") // 12-hour format with AM/PM and seconds
        currentTime.format(formatter)
    }

    Text(
        text = formattedTime,
        style = TypographyCustom.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CurrentTimeDisplayPreview() {
    CurrentTimeDisplay()
}
