package com.example.quranapp.ui.qibla

import androidx.lifecycle.ViewModel
import com.example.quranapp.util.qiblaService.CompassSensorManager
import com.example.quranapp.util.qiblaService.LocationManager
import com.example.quranapp.util.qiblaService.calculateQiblaDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QiblaViewModel @Inject constructor(
    private val locationManager: LocationManager,
    private val compassSensorManager: CompassSensorManager
) : ViewModel() {

    private val _qiblaState = MutableStateFlow(QiblaState())
    val qiblaState = _qiblaState.asStateFlow()

    private val _shouldVibrate = MutableStateFlow(false)
    val shouldVibrate = _shouldVibrate.asStateFlow()

    init {
        startListening()
    }

    fun startListening() {
        locationManager.getLastKnownLocation()
        locationManager.onLocationReceived = { location ->
            updateQiblaDirection(calculateQiblaDirection(location.latitude, location.longitude).toFloat())
        }
        compassSensorManager.onDirectionChanged = { direction ->
            updateCurrentDirection(direction)
        }
        compassSensorManager.registerListeners()
    }

    fun stopListening() {
        compassSensorManager.unregisterListeners()
        locationManager.onLocationReceived = null
        compassSensorManager.onDirectionChanged = null
    }

    private fun updateQiblaDirection(newDirection: Float) {
        _qiblaState.update { it.copy(qiblaDirection = newDirection) }
    }

    private fun updateCurrentDirection(newDirection: Float) {
        _qiblaState.update { state ->
            val directionDifference = (state.qiblaDirection - newDirection + 360) % 360
            val isFacingQibla = directionDifference in 0.0..3.5 || directionDifference >= 356.5

            state.copy(currentDirection = newDirection).also {
                _shouldVibrate.value = isFacingQibla
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopListening()
    }
}
