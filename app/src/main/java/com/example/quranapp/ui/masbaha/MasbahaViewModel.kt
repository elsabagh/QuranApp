package com.example.quranapp.ui.masbaha

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MasbahaViewModel@Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MasbahaUiState())
    val uiState: StateFlow<MasbahaUiState> = _uiState

    fun onCounterClick() {
        _uiState.update { currentState ->
            if (currentState.counterClick < currentState.maxCount) {
                currentState.copy(counterClick = currentState.counterClick + 1)
            } else {
                currentState
            }
        }
    }

    fun onTasbihSelected(id: Int) {
        _uiState.update { currentState ->
            if (currentState.selectedTasbihId != id) {  // ✅ تحقق من عدم تكرار الاختيار
                currentState.copy(
                    selectedTasbihId = id, // ✅ تحديث العنصر المحدد
                    counterClick = 0 // ✅ تصفير العداد عند التغيير
                )
            } else {
                currentState// ✅ لا تحتاج إلى تحديث الحالة إذا تم اختيار نفس العنصر
            }
        }
    }

}
