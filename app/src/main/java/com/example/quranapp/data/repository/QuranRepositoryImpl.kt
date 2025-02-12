package com.example.quranapp.data.repository

import android.util.Log
import com.example.quranapp.data.api.QuranApiService
import com.example.quranapp.data.local.AyahEntity
import com.example.quranapp.data.local.QuranDao
import com.example.quranapp.data.local.SurahEntity
import com.example.quranapp.data.model.Ayah
import com.example.quranapp.data.model.Surah
import com.example.quranapp.data.model.toDomainModel
import com.example.quranapp.domain.repository.QuranRepository
import com.example.quranapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val api: QuranApiService,
    private val surahDao: QuranDao

) : QuranRepository {

    // Fetch the Surah list from the API
    override suspend fun getSurahList(): Flow<Resource<List<Surah>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = api.getSurahList()
                emit(Resource.Success(response.data))
                Log.d("QuranRepository", "Response: $response")
            } catch (e: Exception) {
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
                // Check if Ayahs exist in the local database
                val localAyahs = surahDao.getAyahs(surahNumber)

                if (!localAyahs.isNullOrEmpty()) {
                    // If available locally, return them
                    val ayahs = localAyahs.map { it.toDomainModel() }
                    emit(Resource.Success(ayahs))
                } else {
                    // Otherwise, fetch from API
                    val response = api.getSurahAyahs(surahNumber)

                    if (response.data != null) {
                        val ayahs = response.data.ayahs

                        // Save to local database
                        val ayahEntities = ayahs.map { ayah ->
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

                        emit(Resource.Success(ayahs))
                    } else {
                        emit(Resource.Error("No data available"))
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Error("Failed to fetch data: ${e.message}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }


    // In QuranRepositoryImpl
    override suspend fun downloadSurah(surahNumber: Int) {
        try {
            // Fetch Surah list
            val response = api.getSurahList()
            val surah = response.data.find { it.number == surahNumber }

            if (surah != null) {
                // Save Surah entity
                val surahEntity = SurahEntity(
                    surahNumber = surah.number,
                    name = surah.name,
                    numberOfAyahs = surah.numberOfAyahs,
                    revelationPlace = surah.revelationType
                )
                surahDao.insertSurah(surahEntity)

                // Fetch and save Ayahs
                val ayahResponse = api.getSurahAyahs(surahNumber) // Fetching Ayahs for the Surah
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

                // Insert Ayahs into the database
                if (ayahs.isNotEmpty()) {
                    surahDao.insertAyahs(ayahs)
                }
            }
        } catch (e: Exception) {
            throw Exception("Failed to download Surah $surahNumber: ${e.message}")
        }
    }

    // Fetch surahs from the local database
    override suspend fun getDownloadedSurahs(): Flow<List<Surah>> {
        return flow {
            val surahs = surahDao.getAllSurahs().map { it.toDomainModel() }
            emit(surahs)
        }
    }

}