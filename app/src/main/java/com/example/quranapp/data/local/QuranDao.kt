package com.example.quranapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuranDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurah(surah: SurahEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAyahs(ayahs: List<AyahEntity>)

    @Query("SELECT * FROM surahs WHERE surahNumber = :surahNumber")
    suspend fun getSurah(surahNumber: Int): SurahEntity?

    @Query("SELECT * FROM ayahs WHERE surahNumber = :surahNumber")
    suspend fun getAyahs(surahNumber: Int): List<AyahEntity>?

    // Fetch all downloaded surahs
    @Query("SELECT * FROM surahs")
    suspend fun getAllSurahs(): List<SurahEntity>
}
