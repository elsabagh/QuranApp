package com.example.quranapp.di

import com.example.quranapp.data.api.PrayerApiService
import com.example.quranapp.data.api.QuranApiService
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
object NetworkModule {

    private const val QURAN_BASE_URL = "https://api.alquran.cloud/v1/"
    private const val PRAYER_BASE_URL = "https://api.aladhan.com/"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    @Named("QuranRetrofit")
    fun provideQuranRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(QURAN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    @Named("PrayerRetrofit")
    fun providePrayerRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(PRAYER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideQuranApiService(@Named("QuranRetrofit") retrofit: Retrofit): QuranApiService =
        retrofit.create(QuranApiService::class.java)

    @Provides
    @Singleton
    fun providePrayerApiService(@Named("PrayerRetrofit") retrofit: Retrofit): PrayerApiService =
        retrofit.create(PrayerApiService::class.java)
}
