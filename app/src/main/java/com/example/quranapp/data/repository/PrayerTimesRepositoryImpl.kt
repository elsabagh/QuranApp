package com.example.quranapp.data.repository

import com.example.quranapp.data.api.PrayerApiService
import com.example.quranapp.data.model.PrayerTimesResponse
import com.example.quranapp.domain.repository.PrayerTimesRepository
import com.example.quranapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
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

    override fun getPrayerTimesByDate(
        address: String,
        date: String
    ): Flow<Resource<PrayerTimesResponse>> = flow {
        emit(Resource.Loading(true))

        try {
            val response = apiService.getPrayerTimesByDate(date, address)

            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!)) // ✅ إرسال البيانات الصحيحة
            } else {
                emit(Resource.Error("خطأ في استرجاع البيانات: ${response.message()}"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("خطأ في الشبكة، تأكد من اتصالك بالإنترنت"))
        } catch (e: HttpException) {
            emit(Resource.Error("خطأ في الاتصال بالخادم"))
        } catch (e: Exception) {
            emit(Resource.Error("حدث خطأ غير متوقع: ${e.localizedMessage}"))
        }
    }
}