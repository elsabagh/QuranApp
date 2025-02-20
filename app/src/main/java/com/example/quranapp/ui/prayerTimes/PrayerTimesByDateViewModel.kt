package com.example.quranapp.ui.prayerTimes


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.domain.repository.PrayerTimesRepository
import com.example.quranapp.util.FormatDate
import com.example.quranapp.util.Resource
import com.example.quranapp.util.checkIfGpsEnabled
import com.example.quranapp.util.updateLocation
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerTimesByDateViewModel @Inject constructor(
    private val repository: PrayerTimesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PrayerTimesUiState>(PrayerTimesUiState.Loading)
    val uiState: StateFlow<PrayerTimesUiState> = _uiState

    private val _selectedAddress = MutableStateFlow("Cairo, Egypt") // العنوان الافتراضي
    val selectedAddress: StateFlow<String> = _selectedAddress

    fun updateAddress(address: String) {
        _selectedAddress.value = address
        fetchPrayerTimes(address, FormatDate.getTodayDate()) // تحديث البيانات عند تغيير العنوان
    }

    fun fetchPrayerTimes(address: String, date: String) {
        val timestamp = FormatDate.convertDateToTimestamp(date)
        viewModelScope.launch {
            repository.getPrayerTimesByDate(address, timestamp).collect { result ->
                when (result) {
                    is Resource.Loading -> _uiState.value = PrayerTimesUiState.Loading
                    is Resource.Success -> result.data?.let { data ->
                        _uiState.value = PrayerTimesUiState.Success(data)
                    } ?: run {
                        _uiState.value = PrayerTimesUiState.Error("لم يتم العثور على البيانات!")
                    }
                    is Resource.Error -> _uiState.value = PrayerTimesUiState.Error(result.message ?: "حدث خطأ غير معروف")
                }
            }
        }
    }

    fun fetchLocationAutomatically(fusedLocationClient: FusedLocationProviderClient, context: Context) {
        if (checkIfGpsEnabled(context)) {
            updateLocation(fusedLocationClient, context) { actualAddress ->
                updateAddress(actualAddress) // استخدم العنوان الفعلي
            }
        } else {
            updateAddress("Cairo, Egypt") // استخدم العنوان الافتراضي
        }
    }
}
