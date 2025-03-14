package com.example.quranapp.util.qiblaService

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * كلاس لإدارة الموقع الحالي للجهاز باستخدام FusedLocationProviderClient
 *
 * @property context السياق الخاص بالتطبيق للحصول على الموارد
 * @property fusedLocationClient المسؤول عن جلب الموقع باستخدام Google Play Services
 */
class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) {

    /**
     * دالة تستدعى عند الحصول على الموقع الناجح
     */
    var onLocationReceived: ((Location) -> Unit)? = null

    /**
     * تقوم بجلب الموقع الحالي للجهاز باستخدام أعلى دقة متاحة
     * - **يجب التحقق من الصلاحيات قبل الاستدعاء**
     * - **تستخدم `PRIORITY_HIGH_ACCURACY` للحصول على دقة عالية**
     */
    fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    onLocationReceived?.invoke(location)
                } else {
                    // استخدم آخر موقع معروف في حالة عدم وجود إنترنت
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { lastKnownLocation ->
                            lastKnownLocation?.let { onLocationReceived?.invoke(it) }
                        }
                }
            }
    }
}
