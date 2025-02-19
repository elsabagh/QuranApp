package com.example.quranapp.ui.prayerTimes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.domain.repository.PrayerTimesRepository
import com.example.quranapp.util.FormatDate
import com.example.quranapp.util.Resource
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

    fun fetchPrayerTimes(address: String, date: String) {
        val timestamp = FormatDate.convertDateToTimestamp(date) // تحويل التاريخ إلى timestamp
        Log.d("PrayerTimesViewModel", "Fetching data for date: $timestamp")

        viewModelScope.launch {
            repository.getPrayerTimesByDate(address, timestamp).collect { result ->
                when (result) {
                    is Resource.Loading -> _uiState.value = PrayerTimesUiState.Loading
                    is Resource.Success -> result.data?.let { data ->
                        Log.d("PrayerTimesViewModel", "Final received date: ${data.data.date.readable}")
                        _uiState.value = PrayerTimesUiState.Success(data)
                    } ?: run {
                        _uiState.value = PrayerTimesUiState.Error("لم يتم العثور على البيانات!")
                    }
                    is Resource.Error -> _uiState.value = PrayerTimesUiState.Error(result.message ?: "حدث خطأ غير معروف")
                }
            }
        }
    }
}
