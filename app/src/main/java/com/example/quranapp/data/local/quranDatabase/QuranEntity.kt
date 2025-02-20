package com.example.quranapp.data.local.quranDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quranapp.data.model.Ayah
import com.example.quranapp.data.model.Surah

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

fun SurahEntity.toDomainModel(): Surah {
    return Surah(
        ayahs = emptyList(), // You might fetch the Ayahs separately when needed
        englishName = "", // If you store this in the database, map it here
        name = this.name,
        number = this.surahNumber,
        revelationType = this.revelationPlace,
        numberOfAyahs = this.numberOfAyahs
    )
}


fun AyahEntity.toDomainModel(): Ayah {
    return Ayah(
        hizbQuarter = 0, // Add correct mapping if needed
        juz = this.juz,
        manzil = this.manzil,
        number = this.id,
        numberInSurah = this.ayahNumber,
        page = 0, // Add correct mapping if needed
        ruku = 0, // Add correct mapping if needed
        text = this.text,
        surah = Surah( // Dummy Surah, since it is usually fetched separately
            ayahs = emptyList(),
            englishName = "",
            name = "",
            number = this.surahNumber,
            revelationType = "",
            numberOfAyahs = 0
        ),
        sajda = null
    )
}

