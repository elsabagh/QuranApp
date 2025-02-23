package com.example.quranapp.ui.prayerTimes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranapp.data.model.GregorianDate
import com.example.quranapp.data.model.HijriDate
import com.example.quranapp.data.model.PrayerDate
import com.example.quranapp.data.model.PrayerTimesData
import com.example.quranapp.data.model.PrayerTimesResponse
import com.example.quranapp.data.model.Timings
import com.example.quranapp.data.model.Weekday

@Composable
fun PrayerTimesList(prayerTimes: PrayerTimesResponse) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val correctDate =
            prayerTimes.data.date.hijri.weekday.arabic + " - " + prayerTimes.data.date.hijri.fullDate + " هـ"
        Text(
            text = correctDate,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            color = MaterialTheme.colorScheme.onPrimaryContainer

        )
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
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name, fontSize = 18.sp, fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = time, fontSize = 18.sp, fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPrayerTimeItem() {
    PrayerTimeItem("الفجر", "05:00 AM")
}


@Preview(showBackground = true)
@Composable
fun PreviewPrayerTimesList() {
    val samplePrayerTimes = PrayerTimesResponse(
        data = PrayerTimesData(
            timings = Timings(
                Fajr = "05:00 AM",
                Dhuhr = "12:00 PM",
                Asr = "03:00 PM",
                Maghrib = "06:00 PM",
                Isha = "08:00 PM"
            ),
            date = PrayerDate(
                readable = "2024-01-01",
                timestamp = "1672444800",
                hijri = HijriDate(
                    fullDate = "1446-08-09",
                    weekday = Weekday(english = "Monday", arabic = "الإثنين")
                ),
                gregorian = GregorianDate(
                    date = "2024-01-01",
                    year = "2024"
                )
            )
        )
    )
    PrayerTimesList(prayerTimes = samplePrayerTimes)
}