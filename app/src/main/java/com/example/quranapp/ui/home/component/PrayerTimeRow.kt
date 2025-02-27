package com.example.quranapp.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.quranapp.R
import com.example.quranapp.data.model.Timings

@Composable
fun PrayerTimeRow(name: String, time: String, icon: ImageVector) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium
        )
        Icon(imageVector = icon, contentDescription = name)
        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun PrayerTimesGrid(timings: Timings) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf(
            stringResource(R.string.fajr) to timings.Fajr,
            stringResource(R.string.dhuhr) to timings.Dhuhr,
            stringResource(R.string.asr) to timings.Asr,
            stringResource(R.string.maghrib) to timings.Maghrib,
            stringResource(R.string.isha) to timings.Isha
        ).forEach { (name, time) ->
            PrayerTimeRow(name, time, Icons.Filled.WbSunny)
        }
    }
}
