package com.example.quranapp.ui.setting

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _language = MutableStateFlow<Resource<String>>(Resource.Loading()) // أول حالة تحميل
    val language: StateFlow<Resource<String>> = _language.asStateFlow()

    init {
        viewModelScope.launch {
            settingsDataStore.getLanguage().collect { lang ->
                _language.value = Resource.Success(lang) // نجاح تحميل اللغة
            }
        }
    }

    fun changeLanguage(newLanguage: String, context: Context) {
        viewModelScope.launch {
            _language.value = Resource.Loading() // تشغيل اللودينج

            try {
                settingsDataStore.saveLanguage(newLanguage)
                setAppLocale(newLanguage, context)
                _language.value = Resource.Success(newLanguage) // نجاح
            } catch (e: Exception) {
                _language.value = Resource.Error("فشل تغيير اللغة", newLanguage) // خطأ
            }
        }
    }

    private fun setAppLocale(language: String, context: Context) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
