package com.example.quranapp.util

fun convertToArabicNumerals(number: Int): String {
    val arabicDigits = arrayOf("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")
    return number.toString().map { arabicDigits[it - '0'] }.joinToString("")
}