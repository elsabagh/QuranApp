package com.example.quranapp.ui.home

import com.example.quranapp.data.model.PrayerTimesResponse
import com.example.quranapp.util.Resource

data class PrayerTimesState(
    val prayerTimes: Resource<PrayerTimesResponse?> = Resource.Loading(),
    val location: String = "Cairo, Egypt",
    val isRefreshing: Boolean = false,
    val isGpsEnabled: Boolean = true,
    val showGpsDialog: Boolean = false
)
