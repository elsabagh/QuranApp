package com.example.quranapp.data.model


import com.google.gson.annotations.SerializedName

data class PrayerTimesResponse(
    @SerializedName("data") val data: PrayerTimesData
)

data class PrayerTimesData(
    @SerializedName("timings") val timings: Timings,
    @SerializedName("date") val date: PrayerDate
)

data class Timings(
    val Fajr: String,
    val Dhuhr: String,
    val Asr: String,
    val Maghrib: String,
    val Isha: String
)

data class PrayerDate(
    val readable: String,
    val timestamp: String,
    val hijri: HijriDate,
    val gregorian: GregorianDate
)

data class GregorianDate(
    val date: String,  // ✅ يجب أن تكون هنا!
    val year: String
)

data class HijriDate(
    @SerializedName("date") val fullDate: String, // ✅ تاريخ هجري كامل مثل "1446-08-09"
    @SerializedName("weekday") val weekday: Weekday // ✅ اسم اليوم
)

data class Weekday(
    @SerializedName("en") val english: String, // ✅ اسم اليوم بالإنجليزية
    @SerializedName("ar") val arabic: String // ✅ اسم اليوم بالعربية
)
