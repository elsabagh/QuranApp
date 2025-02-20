package com.example.quranapp.di

import com.example.quranapp.data.api.PrayerApiService
import com.example.quranapp.data.api.QuranApiService
import com.example.quranapp.data.local.quranDatabase.QuranDao
import com.example.quranapp.data.repository.PrayerTimesRepositoryImpl
import com.example.quranapp.data.repository.QuranRepositoryImpl
import com.example.quranapp.domain.repository.PrayerTimesRepository
import com.example.quranapp.domain.repository.QuranRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePrayerTimesRepository(prayerApiService: PrayerApiService): PrayerTimesRepository =
        PrayerTimesRepositoryImpl(prayerApiService)

    @Provides
    @Singleton
    fun provideQuranRepository(
        quranApiService: QuranApiService,
        surahDao: QuranDao
    ): QuranRepository =
        QuranRepositoryImpl(quranApiService, surahDao)
}
