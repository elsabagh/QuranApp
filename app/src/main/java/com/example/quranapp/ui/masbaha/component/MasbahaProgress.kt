package com.example.quranapp.ui.masbaha.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MasbahaProgress(
    onCounterClick: () -> Unit,
    counterClick: Int,
    maxCount: Int, // العدد الأقصى من التسبيحة المختارة
    progress: Float, // Value from 0f to 1f
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(240.dp) // Adjust size as needed
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 14.dp.toPx()
            val startAngle = 90f
            val sweepAngle = 360f * progress
            val circleRadius = 105.dp.toPx() // حجم الدائرة الداخلية

            // Background arc
            drawArc(
                color = Color.LightGray,
                startAngle = startAngle,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                color = Color.DarkGray,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            // Central circle
            drawCircle(
                color = Color.LightGray,
                radius = circleRadius,
            )
        }

        // زر مخفي فوق الدائرة لزيادة العداد عند النقر
        Box(
            modifier = Modifier
                .clip(
                    shape = CircleShape
                )
                .size(200.dp)
                .clickable { onCounterClick() } // زيادة العداد عند الضغط
                .background(Color.Transparent),
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$counterClick / $maxCount", // ✅ إضافة maxCount
                fontSize = 36.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPrayerTimeProgress() {
    MasbahaProgress(
        onCounterClick = {},
        counterClick = 24,
        maxCount = 33, // Example max count
        progress = 0.85f, // Example progress
        modifier = Modifier.padding(16.dp)
    )
}
