package com.example.quranapp.ui.prayerTimes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranapp.data.model.PrayerTimesResponse

@Composable
fun PrayerTimesList(prayerTimes: PrayerTimesResponse) {
    Column(modifier = Modifier.padding(16.dp)) {
        val correctDate = prayerTimes.data.date.gregorian.date
        Text(text = "مواقيت الصلاة - $correctDate", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        val timings = prayerTimes.data.timings
        PrayerTimeItem("الفجر", timings.Fajr)
        PrayerTimeItem("الظهر", timings.Dhuhr)
        PrayerTimeItem("العصر", timings.Asr)
        PrayerTimeItem("المغرب", timings.Maghrib)
        PrayerTimeItem("العشاء", timings.Isha)
    }
}

@Composable
fun PrayerTimeItem(name: String, time: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text(text = time, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.Blue)
    }
}
