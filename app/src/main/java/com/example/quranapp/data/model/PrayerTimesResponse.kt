package com.example.quranapp.data.model


import com.google.gson.annotations.SerializedName

data class PrayerTimesResponse(
    @SerializedName("data") val data: PrayerTimesData
)

data class PrayerTimesData(
    @SerializedName("timings") val timings: Timings,
    @SerializedName("date") val date: PrayerDate // ✅ يحتوي على معلومات التاريخ
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

data class DateData(
    @SerializedName("readable") val readable: String, // ✅ عرض التاريخ بصيغة مفهومة (مثلاً: 19 فبراير 2025)
    @SerializedName("gregorian") val gregorian: GregorianDate,
    @SerializedName("hijri") val hijri: HijriDate
)

//data class GregorianDate(
//    @SerializedName("date") val fullDate: String, // ✅ تاريخ جريجوري كامل مثل "2025-02-19"
//    @SerializedName("weekday") val weekday: Weekday
//)

data class HijriDate(
    @SerializedName("date") val fullDate: String, // ✅ تاريخ هجري كامل مثل "1446-08-09"
    @SerializedName("weekday") val weekday: Weekday
)

data class Weekday(
    @SerializedName("en") val english: String, // ✅ اسم اليوم بالإنجليزية
    @SerializedName("ar") val arabic: String // ✅ اسم اليوم بالعربية
)
