package com.example.quranapp.ui.qibla

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quranapp.R
import com.example.quranapp.util.formate.drawableToBitmap

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QiblaScreen(
    navController: NavController,
    onBackClick: () -> Unit
) {
    val viewModel = hiltViewModel<QiblaViewModel>()
    val state by viewModel.qiblaState.collectAsStateWithLifecycle()
    val shouldVibrate by viewModel.shouldVibrate.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val compassBgBitmap =
        remember { drawableToBitmap(context, R.drawable.compass_img).asImageBitmap() }
    val qiblaIconBitmap =
        remember { drawableToBitmap(context, R.drawable.qiblaiconpoint).asImageBitmap() }

    LaunchedEffect(Unit) {
        viewModel.startListening()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopListening()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Qibla"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "الاتجاه الحالي: ${state.currentDirection.toInt()}°",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(23.dp))
            QiblaDirectionText(state.qiblaDirection.toInt())
            Spacer(modifier = Modifier.height(36.dp))
            QiblaDirectionIndicator(isFacingQibla = shouldVibrate)
            Spacer(modifier = Modifier.height(64.dp))
            QiblaCompassCanvas(
                qiblaDirection = state.qiblaDirection,
                currentDirection = state.currentDirection,
                compassBgBitmap = compassBgBitmap,
                qiblaIconBitmap = qiblaIconBitmap,
            )
        }
    }
}

@Composable
fun QiblaDirectionText(qiblaDirection: Int) {
    Text(
        text = "Qibla: $qiblaDirection°",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun QiblaDirectionIndicator(isFacingQibla: Boolean) {
    val icon = if (isFacingQibla) R.drawable.goldqaba else R.drawable.arrowup
    val size = if (isFacingQibla) 60.dp else 30.dp

    Icon(
        modifier = Modifier.size(size),
        painter = painterResource(id = icon),
        contentDescription = null
    )

}

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

@Preview(showBackground = true)
@Composable
fun PreviewQiblaDirectionText() {
    QiblaDirectionText(qiblaDirection = 45)
}

@Preview(showBackground = true)
@Composable
fun PreviewQiblaDirectionIndicator() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        QiblaDirectionIndicator(isFacingQibla = false)
        QiblaDirectionIndicator(isFacingQibla = true)
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
