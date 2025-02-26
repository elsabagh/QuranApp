package com.example.quranapp.util.qiblaService

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import javax.inject.Inject

/**
 * كلاس مسؤول عن قراءة مستشعرات البوصلة لتحديد الاتجاه
 *
 * @property sensorManager مدير المستشعرات في النظام
 */
class CompassSensorManager @Inject constructor(
    private val sensorManager: SensorManager
) : SensorEventListener {

    private val accelerometerReading = FloatArray(3)  // تخزين بيانات التسارع
    private val magnetometerReading = FloatArray(3)  // تخزين بيانات المجال المغناطيسي
    private val rotationMatrix = FloatArray(9)  // مصفوفة التحويل
    private val orientationAngles = FloatArray(3)  // الزوايا المحسوبة من المستشعرات

    /**
     * دالة تستدعى عند تحديث الاتجاه
     */
    var onDirectionChanged: ((Float) -> Unit)? = null

    /**
     * تسجيل المستشعرات لبدء استقبال التحديثات
     */
    fun registerListeners() {
        // التسجيل لمستشعر التسارع
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        }
        // التسجيل لمستشعر المجال المغناطيسي
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magnetometer ->
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    /**
     * إلغاء تسجيل المستشعرات عند عدم الحاجة
     */
    fun unregisterListeners() {
        sensorManager.unregisterListener(this) // إيقاف الاستماع عند عدم الحاجة لتوفير البطارية
    }

    /**
     * مرشح تمرير منخفض لتنعيم البيانات
     */
    private fun lowPassFilter(input: FloatArray, output: FloatArray?): FloatArray {
        if (output == null) return input
        for (i in input.indices) {
            output[i] = output[i] + 0.1f * (input[i] - output[i]) // تطبيق فلتر لتنعيم القيم
        }
        return output
    }

    /**
     *  استقبال تحديث من المستشعرات
     */
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (event.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    lowPassFilter(event.values.clone(), accelerometerReading)
                }

                Sensor.TYPE_MAGNETIC_FIELD -> {
                    lowPassFilter(event.values.clone(), magnetometerReading)
                }
            }

            if (SensorManager.getRotationMatrix(
                    rotationMatrix,
                    null,
                    accelerometerReading,
                    magnetometerReading
                )
            ) {
                SensorManager.getOrientation(rotationMatrix, orientationAngles)
                val azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
                val correctedAzimuth = (azimuth + 360) % 360  // تأكد من أن القيم موجبة فقط
                Log.d("CompassSensorManager", "Current Direction: $correctedAzimuth")
                onDirectionChanged?.invoke(correctedAzimuth)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // يمكن استخدامه لتنبيه المستخدم في حالة الدقة الضعيفة
    }
}

