package com.example.quranapp.data.api

import com.example.quranapp.data.model.SurahDetailsResponse
import com.example.quranapp.data.model.SurahResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiService {
    @GET("surah")
    suspend fun getSurahList(): SurahResponse

    @GET("/surah/{surahNumber}")
    suspend fun getSurahAyahs(@Path("surahNumber") surahNumber: Int): SurahDetailsResponse

}

