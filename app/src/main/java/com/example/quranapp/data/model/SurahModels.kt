package com.example.quranapp.data.model

data class SurahResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: List<Surah>,
)

data class SurahDetailsResponse(
    val data: SurahData
)

data class SurahData(
    val ayahs: List<Ayah>
)