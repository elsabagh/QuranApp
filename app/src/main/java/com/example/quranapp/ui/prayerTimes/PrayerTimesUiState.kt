package com.example.quranapp.ui.prayerTimes

import com.example.quranapp.data.model.PrayerTimesResponse

sealed class PrayerTimesUiState {
    object Loading : PrayerTimesUiState()
    data class Success(val data: PrayerTimesResponse) : PrayerTimesUiState()
    data class Error(val message: String) : PrayerTimesUiState()
}
