package com.example.quranapp.data.model

import com.example.quranapp.data.local.SurahEntity

//data class Surah(
//    val ayahs: List<Ayah>,
//    val englishName: String,
//    val englishNameTranslation: String,
//    val name: String,
//    val number: Int,
//    val revelationType: String,
//    val numberOfAyahs: Int,
//)

data class Surah(
    val ayahs: List<Ayah>?, // Default to an empty list
    val englishName: String,
    val name: String,
    val number: Int,
    val revelationType: String,
    val numberOfAyahs: Int
)

fun Surah.toEntity(): SurahEntity {
    return SurahEntity(
        surahNumber = this.number,
        name = this.name,
        numberOfAyahs = this.numberOfAyahs,
        revelationPlace = this.revelationType // Assuming revelationType maps directly
    )
}

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

