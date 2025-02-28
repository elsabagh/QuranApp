package com.example.quranapp.ui.qibla

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quranapp.R
import com.example.quranapp.ui.qibla.component.QiblaCompassCanvas
import com.example.quranapp.ui.qibla.component.QiblaDirectionIndicator
import com.example.quranapp.ui.qibla.component.QiblaDirectionText
import com.example.quranapp.ui.qibla.component.QiblaWarningDialog
import com.example.quranapp.util.NetworkUtils
import com.example.quranapp.util.checkIfGpsEnabled
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
    var showDialog by remember { mutableStateOf(false) }

    val compassBgBitmap =
        remember { drawableToBitmap(context, R.drawable.compass_img).asImageBitmap() }
    val qiblaIconBitmap =
        remember { drawableToBitmap(context, R.drawable.qiblaiconpoint).asImageBitmap() }

    // ✅ تحقق من الإنترنت و GPS عند فتح الشاشة
    LaunchedEffect(Unit) {
        val isGpsEnabled = checkIfGpsEnabled(context)
        val isNetworkAvailable = NetworkUtils.isNetworkAvailable(context)
        showDialog = !(isGpsEnabled && isNetworkAvailable)

        if (!showDialog) {
            viewModel.startListening()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopListening()
        }
    }

    // ✅ عرض التحذير إذا كان الإنترنت أو GPS مغلقين
    QiblaWarningDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onRetry = {
            val isGpsEnabled = checkIfGpsEnabled(context)
            val isNetworkAvailable = NetworkUtils.isNetworkAvailable(context)

            if (isGpsEnabled && isNetworkAvailable) {
                showDialog = false
                viewModel.startListening() // إعادة تشغيل الاستماع لاتجاه القبلة
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.qibla)) },
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
                text = "${state.currentDirection.toInt()}°",
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
