package com.example.quranapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.quranapp.data.api.PrayerApiService
import com.example.quranapp.data.api.QuranApiService
import com.example.quranapp.data.local.AppDatabase
import com.example.quranapp.data.local.MIGRATION_1_2
import com.example.quranapp.data.local.QuranDao
import com.example.quranapp.data.repository.PrayerTimesRepositoryImpl
import com.example.quranapp.data.repository.QuranRepositoryImpl
import com.example.quranapp.domain.repository.PrayerTimesRepository
import com.example.quranapp.domain.repository.QuranRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val QURAN_BASE_URL = "https://api.alquran.cloud/v1/"
    private val PRAYER_BASE_URL = "https://api.aladhan.com/"

    /** Provide Quran Retrofit **/
    @Singleton
    @Provides
    @Named("QuranRetrofit")
    fun provideQuranRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(QURAN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    /** Provide Prayer Retrofit **/
    @Singleton
    @Provides
    @Named("PrayerRetrofit")
    fun providePrayerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PRAYER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideContext(app: Application): Context = app.applicationContext


    /** Provide PrayerTimesRepository **/
    @Singleton
    @Provides
    fun providePrayerTimesRepository(prayerApiService: PrayerApiService): PrayerTimesRepository {
        return PrayerTimesRepositoryImpl(prayerApiService) // أو التوفير باستخدام Implementations الخاصة بك
    }

    /** Provide Quran API Service **/
    @Singleton
    @Provides
    fun provideQuranApiService(@Named("QuranRetrofit") retrofit: Retrofit): QuranApiService {
        return retrofit.create(QuranApiService::class.java)
    }

    /** Provide Prayer API Service **/
    @Singleton
    @Provides
    fun providePrayerApiService(@Named("PrayerRetrofit") retrofit: Retrofit): PrayerApiService {
        return retrofit.create(PrayerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java,
            "quran_database"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideSurahDao(database: AppDatabase): QuranDao {
        return database.surahDao()
    }

    @Provides
    @Singleton
    fun provideQuranRepository(
        quranApiService: QuranApiService,
        surahDao: QuranDao
    ): QuranRepository {
        return QuranRepositoryImpl(quranApiService, surahDao)
    }
}
