package com.example.quranapp.util.formate

import androidx.compose.ui.text.AnnotatedString
import com.example.quranapp.data.model.Ayah


/**
 * Estimates the number of Ayahs that can fit in the given height.
 */
fun estimateAyahsPerPage(availableHeight: Int): Int = (availableHeight / 60).coerceAtLeast(1)


fun formatAyahs(
    surahNumber: Int,
    ayahs: List<Ayah>,
    currentPage: Int,
): AnnotatedString {
    val builder = AnnotatedString.Builder()

    // Iterate through the ayahs
    ayahs.forEachIndexed { index, ayah ->
        // Check if we are on the first page and it's the first verse on that page
        val isFirstVerseOnFirstPage = currentPage == 0 && index == 0

        // Format the verse text based on the condition
        val formattedText = when {
            isFirstVerseOnFirstPage && surahNumber != 9 && surahNumber != 1 -> {
                // Split the verse into words and remove the first four words
                val words = ayah.text.trim().split(" ")
                words.drop(4).joinToString(" ") // Remove first four words
            }

            else -> {
                // Keep the full verse
                ayah.text.trim()
            }
        }

        // Convert verse number to Arabic numerals
        val arabicVerseNumber = convertToArabicNumerals(ayah.numberInSurah)

        // Append the formatted verse text and verse number
        builder.append("$formattedText ($arabicVerseNumber) ")
        builder.append("") // Add space between verses
    }

    return builder.toAnnotatedString()
}