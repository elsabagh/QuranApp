package com.example.quranapp.util.qiblaService

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * تحسب اتجاه القبلة بناءً على إحداثيات الموقع الحالي
 * @param latitude خط العرض للموقع الحالي
 * @param longitude خط الطول للموقع الحالي
 * @return زاوية اتجاه القبلة بالدرجات (بين 0 و 360)
 */
fun calculateQiblaDirection(latitude: Double, longitude: Double): Double {
    // إحداثيات الكعبة المشرفة في مكة المكرمة
    val kaabaLatitude = 21.4225
    val kaabaLongitude = 39.8262

    // تحويل الفرق في خط الطول والعرض إلى راديان لزيادة الدقة
    val deltaLongitude = Math.toRadians(kaabaLongitude - longitude)
    val lat1 = Math.toRadians(latitude)
    val lat2 = Math.toRadians(kaabaLatitude)

    // حساب القيم لإيجاد الزاوية باستخدام الدوال المثلثية
    val y = sin(deltaLongitude) * cos(lat2)
    val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(deltaLongitude)

    // حساب الزاوية المطلوبة وتحويلها إلى درجات
    val bearing = Math.toDegrees(atan2(y, x))

    // التأكد من أن الزاوية موجبة دائماً (بين 0 و 360 درجة)
    return (bearing + 360) % 360
}
