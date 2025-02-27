package com.example.quranapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quranapp.ui.home.HomeScreen
import com.example.quranapp.ui.masbaha.MasbahaScreen
import com.example.quranapp.ui.navigation.AppDestination.MasbahaDestination
import com.example.quranapp.ui.navigation.AppDestination.PrayerTimesDestination
import com.example.quranapp.ui.navigation.AppDestination.QiblaDestination
import com.example.quranapp.ui.navigation.AppDestination.QuranHomeDestination
import com.example.quranapp.ui.navigation.AppDestination.QuranPageDestination
import com.example.quranapp.ui.navigation.AppDestination.SettingsDestination
import com.example.quranapp.ui.navigation.AppDestination.SurahDetailsDestination
import com.example.quranapp.ui.prayerTimes.PrayerTimesByDateScreen
import com.example.quranapp.ui.qibla.QiblaScreen
import com.example.quranapp.ui.quranList.QuranListScreen
import com.example.quranapp.ui.setting.SettingScreen
import com.example.quranapp.ui.surahDetails.SurahDetailsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = QuranHomeDestination.route
    ) {
        composable(QuranHomeDestination.route) {
            HomeScreen(
                navController = navController,
            )
        }
        composable(QuranPageDestination.route) {
            QuranListScreen(
                navController = navController,
                onBackClick = {
                    navController.navigate(QuranHomeDestination.route)
                }
            )
        }
        composable(SurahDetailsDestination.route) { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getString("surahNumber")?.toInt() ?: 1
            val surahName = backStackEntry.arguments?.getString("surahName") ?: "Surah Name"
            SurahDetailsScreen(
                surahNumber = surahNumber,
                surahName = surahName,
                onBackClick = { navController.navigate(QuranPageDestination.route) }
            )
        }
        composable(PrayerTimesDestination.route) {
            PrayerTimesByDateScreen(
                navController = navController,
                onBackClick = {
                    navController.navigate(QuranHomeDestination.route)
                }
            )
        }
        composable(MasbahaDestination.route) {
            MasbahaScreen(
                navController = navController,
                onBackClick = {
                    navController.navigate(QuranHomeDestination.route)
                }
            )
        }
        composable(QiblaDestination.route) {
            QiblaScreen(
                navController = navController,
                onBackClick = {
                    navController.navigate(QuranHomeDestination.route)
                }
            )
        }
        composable(SettingsDestination.route) {
         SettingScreen(
             navController = navController,
             onBackClick = {
                 navController.navigate(QuranHomeDestination.route)
             }
         )
        }
    }
}
