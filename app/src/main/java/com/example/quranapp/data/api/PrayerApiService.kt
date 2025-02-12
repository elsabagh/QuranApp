package com.example.quranapp.data.api

import com.example.quranapp.data.model.PrayerTimesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerApiService {
    @GET("v1/timingsByCity")
    suspend fun getPrayerTimes(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("method") method: Int = 5
    ): Response<PrayerTimesResponse>
}