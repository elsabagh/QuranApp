package com.example.quranapp.ui.surahDetails

import com.example.quranapp.data.model.Ayah

data class SurahDetailsState(
    val isLoading: Boolean = false,
    val ayahList: List<Ayah> = emptyList(),
    val error: String? = null,
    val currentPage: Int = 0, // Track the current page
    val totalPages: Int = 1 // Total number of pages
)
