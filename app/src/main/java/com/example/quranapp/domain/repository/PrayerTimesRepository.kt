package com.example.quranapp.domain.repository

import com.example.quranapp.data.model.PrayerTimesResponse
import com.example.quranapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface PrayerTimesRepository {

    fun getPrayerTimes(
        city: String,
        country: String
    ): Flow<Resource<PrayerTimesResponse?>>
}