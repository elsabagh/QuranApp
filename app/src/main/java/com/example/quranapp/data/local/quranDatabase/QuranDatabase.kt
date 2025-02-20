package com.example.quranapp.data.local.quranDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // هنا يمكنك إضافة التغييرات الخاصة بقاعدة البيانات
        database.execSQL("CREATE TABLE IF NOT EXISTS `ayahs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `surahNumber` INTEGER NOT NULL, `ayahNumber` INTEGER NOT NULL, `text` TEXT)")
    }
}
@Database(entities = [SurahEntity::class, AyahEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun surahDao(): QuranDao

}