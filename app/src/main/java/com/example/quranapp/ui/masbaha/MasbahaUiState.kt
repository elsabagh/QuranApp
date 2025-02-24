package com.example.quranapp.ui.masbaha

data class MasbahaUiState(
    val counterClick: Int = 0,
    val maxCount: Int = 33 ,// الحد الأقصى للتسبيحات
    val selectedTasbihId: Int? = null // تخزين معرف العنصر المختار
) {
    val progress: Float
        get() = counterClick.toFloat() / maxCount.toFloat()
}

data class TasbihItem(
    val id: Int, // معرف فريد لكل تسبيحة
    val name: String,
    val count: Int
)

