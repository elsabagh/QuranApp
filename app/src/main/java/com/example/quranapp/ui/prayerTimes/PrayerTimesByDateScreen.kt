package com.example.quranapp.ui.prayerTimes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quranapp.ui.prayerTimes.component.PrayerNavigationControls
import com.example.quranapp.ui.prayerTimes.component.PrayerTimesList
import com.example.quranapp.ui.prayerTimes.component.PrayerTopBar
import com.example.quranapp.util.FormatDate

@Composable
fun PrayerTimesByDateScreen(
    navController: NavController,
    onBackClick: () -> Unit
) {
    val viewModel: PrayerTimesByDateViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var selectedDate by remember { mutableStateOf(FormatDate.getTodayDate()) }
    val address = "Cairo, Egypt"

    LaunchedEffect(selectedDate) {
        viewModel.fetchPrayerTimes(address, selectedDate)
    }
    Scaffold(
        topBar = {
            PrayerTopBar(
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {

            PrayerNavigationControls(
                onNextClick = { selectedDate = FormatDate.getNextDate(selectedDate) },
                onPreviousClick = { selectedDate = FormatDate.getPreviousDate(selectedDate) },
                selectedDate = selectedDate,
                onDateSelected = { date ->
                    selectedDate = date
                } // تحديث التاريخ عند اختياره من الـ DatePicker
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (uiState) {
                is PrayerTimesUiState.Loading -> CircularProgressIndicator()
                is PrayerTimesUiState.Success -> PrayerTimesList((uiState as PrayerTimesUiState.Success).data)
                is PrayerTimesUiState.Error -> Text(
                    text = (uiState as PrayerTimesUiState.Error).message,
                    color = Color.Red
                )
            }
        }
    }
}



