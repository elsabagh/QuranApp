package com.example.quranapp.domain.repository

import com.example.quranapp.data.model.Ayah
import com.example.quranapp.data.model.Surah
import com.example.quranapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    suspend fun getSurahList(): Flow<Resource<List<Surah>>>

    suspend fun getSurahAyahs(surahNumber: Int): Flow<Resource<List<Ayah>>>

    suspend fun downloadSurah(surahNumber: Int)

    suspend fun getDownloadedSurahs(): Flow<List<Surah>>
}