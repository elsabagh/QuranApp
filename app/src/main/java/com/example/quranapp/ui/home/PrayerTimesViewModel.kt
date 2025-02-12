package com.example.quranapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.domain.repository.PrayerTimesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerTimesViewModel @Inject constructor(
    private val repository: PrayerTimesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PrayerTimesState())
    val state: StateFlow<PrayerTimesState> get() = _state

    fun fetchPrayerTimes(city: String, country: String) {
        viewModelScope.launch {
            repository.getPrayerTimes(city, country).collect { response ->
                _state.value = _state.value.copy(prayerTimes = response)
            }
        }
    }

    fun updateLocation(newLocation: String) {
        _state.value = _state.value.copy(location = newLocation)
    }

    fun setRefreshing(isRefreshing: Boolean) {
        _state.value = _state.value.copy(isRefreshing = isRefreshing)
    }

    fun setGpsDialogVisibility(visible: Boolean) {
        _state.value = _state.value.copy(showGpsDialog = visible)
    }
}
