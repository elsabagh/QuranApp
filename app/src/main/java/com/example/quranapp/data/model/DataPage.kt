package com.example.quranapp.data.model

data class PageApiResponse(
    val data: DataPage
)

data class DataPage(
    val ayahs: List<Ayah>, val surahs: Map<String, Surah>
)