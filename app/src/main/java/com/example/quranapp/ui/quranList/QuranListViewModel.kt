package com.example.quranapp.ui.quranList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.domain.repository.QuranRepository
import com.example.quranapp.util.NetworkUtils
import com.example.quranapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranListViewModel @Inject constructor(
    private val quranRepository: QuranRepository,
    private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(QuranListState())
    val state: StateFlow<QuranListState> = _state.asStateFlow()

    private val _selectedFilter = MutableStateFlow(QuranFilterType.ALL)
    val selectedFilter: StateFlow<QuranFilterType> = _selectedFilter

    init {
        viewModelScope.launch {
            checkDownloadedSurahs()  // Ensure downloaded Surahs are loaded
            if (NetworkUtils.isNetworkAvailable(context)) {
                if (_state.value.surahList.isEmpty()) {
                    getSurahList()  // Fetch from API only if not loaded
                }
            } else {
                getDownloadedSurahs()  // Fetch downloaded Surahs if offline
            }
        }
    }

    fun getSurahList() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            quranRepository.getSurahList().collect { resource ->
                when (resource) {
                    is Resource.Error -> _state.update {
                        it.copy(isLoading = false, error = resource.message ?: "Unknown error")
                    }

                    is Resource.Success -> resource.data?.let { surahList ->
                        val updatedSurahList = surahList.map { it.copy(ayahs = it.ayahs ?: emptyList()) }
                        _state.update { it.copy(isLoading = false, surahList = updatedSurahList) }
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = resource.isLoading) }
                }
            }
        }
    }

    private fun getDownloadedSurahs() {
        viewModelScope.launch {
            quranRepository.getDownloadedSurahs().collect { surahList ->
                _state.update { it.copy(isLoading = false, surahList = surahList) }
            }
        }
    }

    fun downloadSurah(surahNumber: Int) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(downloadingSurahNumber = surahNumber) }
                quranRepository.downloadSurah(surahNumber)
                checkDownloadedSurahs() // Refresh the list after downloading
                _state.update { it.copy(downloadingSurahNumber = null) }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Download failed: ${e.message}") }
            }
        }
    }

    fun checkDownloadedSurahs() {
        viewModelScope.launch {
            quranRepository.getDownloadedSurahs().collect { downloadedSurahs ->
                _state.update {
                    it.copy(downloadedSurahNumbers = downloadedSurahs.map { it.number })
                }
            }
        }
    }

    fun setFilter(filter: QuranFilterType) {
        _selectedFilter.value = filter
    }
}
