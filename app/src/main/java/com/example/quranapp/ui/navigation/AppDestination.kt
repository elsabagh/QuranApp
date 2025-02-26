package com.example.quranapp.ui.navigation

interface AppDestination {
    val route: String

    object QuranHomeDestination : AppDestination {
        override val route = "HomeScreen"
    }

    object QuranPageDestination : AppDestination {
        override val route = "QuranListScreen"
    }

    object SurahDetailsDestination : AppDestination {
        override val route = "surahDetails/{surahNumber}/{surahName}"
    }

    object PrayerTimesDestination : AppDestination {
        override val route = "PrayerTimesScreen"
    }
    object MasbahaDestination : AppDestination {
        override val route = "MasbahaScreen"
    }

    object QiblaDestination : AppDestination {
        override val route = "QiblaScreen"
    }
}