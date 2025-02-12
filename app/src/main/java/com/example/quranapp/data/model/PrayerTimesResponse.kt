package com.example.quranapp.data.model

data class PrayerTimesResponse(
    val data: TimingsData
)

data class TimingsData(
    val timings: Timings
)

data class Timings(
    val Fajr: String,
    val Dhuhr: String,
    val Asr: String,
    val Maghrib: String,
    val Isha: String
)
