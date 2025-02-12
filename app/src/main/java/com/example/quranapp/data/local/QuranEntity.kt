package com.example.quranapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "surahs")
data class SurahEntity(
    @PrimaryKey val surahNumber: Int,
    val name: String,
    val numberOfAyahs: Int,
    val revelationPlace: String // Changed to revelationType from Surah
)

@Entity(tableName = "ayahs")
data class AyahEntity(
    @PrimaryKey val id: Int,
    val surahNumber: Int,
    val ayahNumber: Int,
    val text: String,
    val juz: Int,
    val manzil: Int
)
