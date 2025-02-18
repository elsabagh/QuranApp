package com.example.quranapp.di

import android.app.Application
import androidx.room.Room
import com.example.quranapp.data.local.AppDatabase
import com.example.quranapp.data.local.MIGRATION_1_2
import com.example.quranapp.data.local.QuranDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java,
            "quran_database"
        ).addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    @Singleton
    fun provideSurahDao(database: AppDatabase): QuranDao = database.surahDao()
}
