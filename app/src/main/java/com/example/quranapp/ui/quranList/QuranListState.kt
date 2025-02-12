package com.example.quranapp.ui.quranList

import com.example.quranapp.data.model.Surah

data class QuranListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val surahList: List<Surah> = emptyList(),
    val downloadingSurahNumber: Int? = null, // Track the surah number being downloaded
    val downloadedSurahNumbers: List<Int> = emptyList() // List of downloaded Surah numbers

)
