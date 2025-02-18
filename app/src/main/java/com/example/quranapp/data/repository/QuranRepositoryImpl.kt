package com.example.quranapp.data.repository

import android.util.Log
import com.example.quranapp.data.api.QuranApiService
import com.example.quranapp.data.local.AyahEntity
import com.example.quranapp.data.local.QuranDao
import com.example.quranapp.data.local.SurahEntity
import com.example.quranapp.data.local.toDomainModel
import com.example.quranapp.data.model.Ayah
import com.example.quranapp.data.model.Surah
import com.example.quranapp.domain.repository.QuranRepository
import com.example.quranapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val api: QuranApiService,
    private val surahDao: QuranDao
) : QuranRepository {

    override suspend fun getSurahList(): Flow<Resource<List<Surah>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val response = api.getSurahList()
                emit(Resource.Success(response.data))
                Log.d("QuranRepository", "Response: $response")
            } catch (e: Exception) {
                Log.e("QuranRepository", "Error fetching surahs: ${e.message}")
                emit(Resource.Error(e.message ?: "Unknown error"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getSurahAyahs(surahNumber: Int): Flow<Resource<List<Ayah>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val localAyahs = surahDao.getAyahs(surahNumber)

                if (!localAyahs.isNullOrEmpty()) {
                    emit(Resource.Success(localAyahs.map { it.toDomainModel() }))
                } else {
                    val response = api.getSurahAyahs(surahNumber)

                    response.data?.let {
                        val ayahEntities = it.ayahs.map { ayah ->
                            AyahEntity(
                                id = ayah.number,
                                surahNumber = surahNumber,
                                ayahNumber = ayah.numberInSurah,
                                text = ayah.text,
                                juz = ayah.juz,
                                manzil = ayah.manzil
                            )
                        }

                        surahDao.insertAyahs(ayahEntities)
                        emit(Resource.Success(it.ayahs))
                    } ?: emit(Resource.Error("No data available"))
                }
            } catch (e: Exception) {
                Log.e("QuranRepository", "Error fetching ayahs: ${e.message}")
                emit(Resource.Error("Failed to fetch data: ${e.message}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun downloadSurah(surahNumber: Int) {
        try {
            val response = api.getSurahList()
            val surah = response.data.find { it.number == surahNumber }

            surah?.let {
                val surahEntity = SurahEntity(
                    surahNumber = it.number,
                    name = it.name,
                    numberOfAyahs = it.numberOfAyahs,
                    revelationPlace = it.revelationType
                )
                surahDao.insertSurah(listOf(surahEntity)) // ✅ تحويل الكائن إلى قائمة

                val ayahResponse = api.getSurahAyahs(surahNumber)
                val ayahs = ayahResponse.data?.ayahs?.map { ayah ->
                    AyahEntity(
                        id = ayah.number,
                        surahNumber = surahNumber,
                        ayahNumber = ayah.numberInSurah,
                        text = ayah.text,
                        juz = ayah.juz,
                        manzil = ayah.manzil
                    )
                } ?: emptyList()

                if (ayahs.isNotEmpty()) {
                    surahDao.insertAyahs(ayahs)
                }
            } ?: Log.e("QuranRepository", "Surah not found in API")

        } catch (e: Exception) {
            Log.e("QuranRepository", "Failed to download Surah $surahNumber: ${e.message}")
            throw Exception("Failed to download Surah $surahNumber: ${e.message}")
        }
    }

    override suspend fun getDownloadedSurahs(): Flow<List<Surah>> {
        return flow {
            val surahs = surahDao.getAllSurahs().map { it.toDomainModel() }
            emit(surahs)
        }
    }
}
