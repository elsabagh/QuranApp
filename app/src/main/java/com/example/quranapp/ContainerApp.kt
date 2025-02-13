package com.example.quranapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quranapp.ui.navigation.NavGraph
import com.example.quranapp.ui.theme.QuranAppTheme

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerApp(
    modifier: Modifier = Modifier,
) {

    val appState = rememberAppState()

    QuranAppTheme {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = appState.scaffoldState.snackbarHostState,
                    modifier = Modifier.padding(8.dp),
                    snackbar = { snackBarData ->
                        Snackbar(snackBarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                    }
                )
            }
        ) { innerPadding ->
            NavGraph(
                modifier = modifier.padding(innerPadding)
            )
        }
    }

}