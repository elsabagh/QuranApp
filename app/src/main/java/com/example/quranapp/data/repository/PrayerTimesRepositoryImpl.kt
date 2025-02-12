package com.example.quranapp.data.repository

import com.example.quranapp.data.api.PrayerApiService
import com.example.quranapp.data.model.PrayerTimesResponse
import com.example.quranapp.domain.repository.PrayerTimesRepository
import com.example.quranapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PrayerTimesRepositoryImpl @Inject constructor(
    private val apiService: PrayerApiService
) : PrayerTimesRepository {

    override fun getPrayerTimes(
        city: String,
        country: String
    ): Flow<Resource<PrayerTimesResponse?>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = apiService.getPrayerTimes(city, country)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Error("Error: ${response.message()}")) // Emit error message
            }
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.localizedMessage}")) // Handle exceptions
        }
    }
}