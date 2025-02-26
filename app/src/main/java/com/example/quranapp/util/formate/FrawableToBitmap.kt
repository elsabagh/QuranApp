package com.example.quranapp.util.formate

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

/**
 * تقوم بتحويل صورة من نوع Drawable إلى كائن Bitmap
 * @param context السياق (Context) للحصول على الموارد
 * @param drawableId معرف الصورة في الموارد (R.drawable.image)
 * @return كائن Bitmap يمثل الصورة المحولة
 */
fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
    // الحصول على الصورة ككائن Drawable
    val drawable: Drawable = ContextCompat.getDrawable(context, drawableId)!!

    // تحويلها إلى Bitmap بنفس أبعادها الأصلية
    return drawable.toBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
}
