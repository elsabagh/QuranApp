package com.example.quranapp.ui.home.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quranapp.data.model.PrayerTimesResponse
import com.example.quranapp.ui.theme.Typography
import com.example.quranapp.ui.theme.TypographyCustom
import com.example.quranapp.util.Resource

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrayerTimesCard(location: String, prayerTimes: Resource<PrayerTimesResponse?>) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "وَٱللَّهُ أَعۡلَمُ بِأَعۡدَآئِكُمۡۚ وَكَفَىٰ بِٱللَّهِ وَلِيّٗا وَكَفَىٰ بِٱللَّهِ نَصِيرٗا",
                textAlign = TextAlign.Center,
                style = TypographyCustom.bodySmall,
                modifier = Modifier.padding(16.dp),
            )

            CurrentTimeDisplay(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )


            when (prayerTimes) {
                is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                is Resource.Success -> {
                    prayerTimes.data?.data?.timings?.let { timings ->
                        NextPrayerTime(timings, Modifier)
                        PrayerTimesGrid(timings)
                        Text(location, style = Typography.bodyMedium, modifier = Modifier.padding(bottom = 8.dp))
                    }
                }

                is Resource.Error -> {
                    Text(
                        text = "Error: ${prayerTimes.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

