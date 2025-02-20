package com.example.quranapp.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object FormatDate {

    fun convertDateToTimestamp(date: String): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = sdf.parse(date) ?: return date
            (parsedDate.time / 1000).toString()
        } catch (e: Exception) {
            Log.e("DateUtils", "Error parsing date: $date", e)
            date
        }
    }

    fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getPreviousDate(currentDate: String): String {
        return changeDateByDays(currentDate, -1)
    }

    fun getNextDate(currentDate: String): String {
        return changeDateByDays(currentDate, 1)
    }

    private fun changeDateByDays(date: String, days: Int): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val calendar = Calendar.getInstance().apply {
                time = sdf.parse(date)!!
                add(Calendar.DAY_OF_YEAR, days)
            }
            sdf.format(calendar.time)
        } catch (e: Exception) {
            Log.e("DateUtils", "Error changing date: $date", e)
            date
        }
    }
}
