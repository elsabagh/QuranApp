package com.example.quranapp.ui.qibla.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.quranapp.R
import com.example.quranapp.util.formate.drawableToBitmap

@Composable
fun QiblaCompassCanvas(
    qiblaDirection: Float,
    currentDirection: Float,
    compassBgBitmap: ImageBitmap,
    qiblaIconBitmap: ImageBitmap,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2.5f

            rotate(degrees = -currentDirection, pivot = center) {
                drawImage(
                    image = compassBgBitmap,
                    topLeft = Offset(
                        center.x - compassBgBitmap.width / 2,
                        center.y - compassBgBitmap.height / 2
                    )
                )
            }

            rotate(degrees = qiblaDirection - currentDirection, pivot = center) {

                drawImage(
                    image = qiblaIconBitmap,
                    topLeft = Offset(
                        center.x - qiblaIconBitmap.width / 2,
                        center.y - radius - qiblaIconBitmap.height / .5f
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewQiblaCompassCanvas() {
    val context = LocalContext.current
    val compassBgBitmap =
        remember { drawableToBitmap(context, R.drawable.compass_img).asImageBitmap() }
    val qiblaIconBitmap =
        remember { drawableToBitmap(context, R.drawable.qiblaiconpoint).asImageBitmap() }

    QiblaCompassCanvas(
        qiblaDirection = 45f,
        currentDirection = 0f,
        compassBgBitmap = compassBgBitmap,
        qiblaIconBitmap = qiblaIconBitmap,
    )
}
