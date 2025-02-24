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
import androidx.navigation.compose.rememberNavController
import com.example.quranapp.ui.navigation.NavGraph
import com.example.quranapp.ui.theme.QuranAppTheme

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerApp(
    modifier: Modifier = Modifier,
) {

    val appState = rememberAppState()
    val navController = rememberNavController()
//    // ✅ متغير لتتبع المسار الحالي
//    var currentRoute by rememberSaveable { mutableStateOf<String?>(null) }

//    // ✅ تحديث المسار عند تغييره
//    LaunchedEffect(navController) {
//        navController.currentBackStackEntryFlow.collect { backStackEntry ->
//            currentRoute = backStackEntry.destination.route
//        }
//    }

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
            },
//            bottomBar = {
//                val mainScreens = listOf(
//                    AppDestination.QuranHomeDestination.route,
//                    AppDestination.QuranPageDestination.route,
//                    AppDestination.PrayerTimesDestination.route
//                )
//
//                if (currentRoute in mainScreens) {
//                    BottomNavBar(bottomNavController = navController)
//                }
//            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                modifier = modifier.padding(innerPadding)
            )
        }
    }

}