package com.example.quranapp.ui.surahDetails

import androidx.lifecycle.ViewModel
import com.example.quranapp.data.model.Ayah
import com.example.quranapp.domain.repository.QuranRepository
import com.example.quranapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SurahDetailsViewModel @Inject constructor(
    private val quranRepository: QuranRepository
) : ViewModel() {

    private var _state = MutableStateFlow(SurahDetailsState())
    val state = _state.asStateFlow()

    var ayahsPerPage = 5 // Default value, will be updated dynamically

    suspend fun getSurahAyahs(surahNumber: Int) {
        quranRepository.getSurahAyahs(surahNumber).collect { resource ->
            _state.update {
                when (resource) {
                    is Resource.Error -> it.copy(
                        isLoading = false,
                        error = resource.message ?: "Unknown error"
                    )
                    is Resource.Success -> {
                        val ayahs = resource.data ?: emptyList()
                        val totalPages = if (ayahs.isNotEmpty()) {
                            (ayahs.size + ayahsPerPage - 1) / ayahsPerPage
                        } else {
                            1
                        }
                        it.copy(
                            isLoading = false,
                            ayahList = ayahs,
                            totalPages = totalPages
                        )
                    }
                    is Resource.Loading -> it.copy(isLoading = resource.isLoading)
                }
            }
        }
    }

    fun updateAyahsPerPage(newCount: Int) {
        ayahsPerPage = newCount
        _state.update { it.copy(currentPage = 0, totalPages = (state.value.ayahList.size + ayahsPerPage - 1) / ayahsPerPage) }
    }

    fun getCurrentPageAyahs(): List<Ayah> {
        val startIndex = state.value.currentPage * ayahsPerPage
        val endIndex = (startIndex + ayahsPerPage).coerceAtMost(state.value.ayahList.size)
        return state.value.ayahList.subList(startIndex, endIndex)
    }

    fun goToNextPage() {
        _state.update { it.copy(currentPage = (it.currentPage + 1).coerceAtMost(it.totalPages - 1)) }
    }

    fun goToPreviousPage() {
        _state.update { it.copy(currentPage = (it.currentPage - 1).coerceAtLeast(0)) }
    }
}