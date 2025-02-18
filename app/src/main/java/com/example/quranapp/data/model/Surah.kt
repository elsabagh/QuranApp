package com.example.quranapp.data.model

data class Surah(
    val ayahs: List<Ayah>?, // Default to an empty list
    val englishName: String,
    val name: String,
    val number: Int,
    val revelationType: String,
    val numberOfAyahs: Int
)
