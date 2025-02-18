package com.example.quranapp.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.json.JsonObject

data class AyahApiResponse(
    val data: Ayah,
)

data class Ayah(
    val hizbQuarter: Int,
    val juz: Int,
    val manzil: Int,
    val number: Int,
    val numberInSurah: Int,
    val page: Int,
    val ruku: Int,
    val text: String,
    val surah: Surah,
    @SerializedName("sajda") val sajda: Any? // Handle both boolean and object
) {
    fun isSajda(): Boolean {
        return when (sajda) {
            is Boolean -> sajda
            is JsonObject -> true  // If it's an object, treat it as true
            else -> false
        }
    }
}

