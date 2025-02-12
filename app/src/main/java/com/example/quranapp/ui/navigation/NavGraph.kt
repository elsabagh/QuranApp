package com.example.auth_firebase.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quranapp.ui.home.HomeScreen
import com.example.quranapp.ui.quranList.QuranListScreen
import com.example.quranapp.ui.surahDetails.SurahDetailsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppDestination.QuranHomeDestination.route
    ) {
        composable(AppDestination.QuranHomeDestination.route) {
            HomeScreen(
                navController = navController
            )
        }
        composable(AppDestination.QuranPageDestination.route) {
            QuranListScreen(
                navController = navController,
                onBackClick = {
                    navController.navigate(AppDestination.QuranHomeDestination.route)
                }
            )
        }
        composable( AppDestination.SurahDetailsDestination.route) { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getString("surahNumber")?.toInt() ?: 1
            val surahName = backStackEntry.arguments?.getString("surahName") ?: "Surah Name"
            SurahDetailsScreen(
                surahNumber = surahNumber,
                surahName = surahName,
                onBackClick = { navController.navigate(AppDestination.QuranPageDestination.route) }
            )
        }
    }
}
