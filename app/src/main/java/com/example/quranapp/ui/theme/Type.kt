package com.example.quranapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.example.quranapp.R

val kitabFont = FontFamily(
    Font(R.font.kitab_regular, FontWeight.Normal), Font(R.font.kitab_bold, FontWeight.Bold)
)

val TypographyCustom = Typography(
    bodyLarge = TextStyle(
        fontFamily = kitabFont,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Justify,
        textDirection = TextDirection.Rtl
    ),
    bodyMedium = TextStyle(
        fontFamily = kitabFont,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Justify,
        textDirection = TextDirection.Rtl
    ),
    bodySmall = TextStyle(
        fontFamily = kitabFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Justify,
        textDirection = TextDirection.Rtl
    )
)


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = kitabFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        textDirection = TextDirection.Rtl
    ),
    bodyMedium = TextStyle(
        fontFamily = kitabFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        textDirection = TextDirection.Rtl
    ),
    bodySmall = TextStyle(
        fontFamily = kitabFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        textDirection = TextDirection.Rtl
    ),
)
