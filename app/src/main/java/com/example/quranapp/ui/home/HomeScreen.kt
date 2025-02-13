package com.example.quranapp.ui.home

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quranapp.R
import com.example.quranapp.data.model.PrayerTimesResponse
import com.example.quranapp.ui.home.component.ActionRow
import com.example.quranapp.ui.home.component.DialogLocation
import com.example.quranapp.ui.home.component.DuaCard
import com.example.quranapp.ui.home.component.PrayerTimesCard
import com.example.quranapp.ui.navigation.AppDestination
import com.example.quranapp.ui.theme.TypographyCustom
import com.example.quranapp.util.Resource
import com.example.quranapp.util.updateLocation
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.location.LocationServices


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<PrayerTimesViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(RequestPermission()) { granted ->
            if (granted) {
                updateLocation(fusedLocationClient, context) { viewModel.updateLocation(it) }
            }
        }

    LaunchedEffect(Unit) {
        if (!state.isGpsEnabled) viewModel.setGpsDialogVisibility(true)
        else locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(state.location) {
        val (city, country) = state.location.split(", ")
        viewModel.fetchPrayerTimes(city, country)
    }

    if (state.showGpsDialog) {
        DialogLocation(onDismiss = { viewModel.setGpsDialogVisibility(false) })
    }

    Scaffold(
        topBar = { HomeTopBar(onMenuClick = {}) }
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.isRefreshing),
            onRefresh = {
                viewModel.setRefreshing(true)
                updateLocation(fusedLocationClient, context) {
                    viewModel.updateLocation(it)
                    viewModel.setRefreshing(false)
                }
            }
        ) {
            HomeContent(
                state.location, state.prayerTimes,
                modifier = Modifier.padding(paddingValues),
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "", style = TypographyCustom.bodyMedium)
        },
        actions = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContent(
    location: String,
    prayerTimes: Resource<PrayerTimesResponse?>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrayerTimesCard(location, prayerTimes)

        ActionRow(
            actions = listOf(
                Triple(
                    stringResource(R.string.quran),
                    painterResource(id = R.drawable.quran),
                    AppDestination.QuranPageDestination.route
                ),
                Triple(
                    stringResource(R.string.azkar),
                    painterResource(id = R.drawable.azkar),
                    "azkarScreen"
                )
            ),
            navController = navController
        )

        ActionRow(
            actions = listOf(
                Triple(
                    stringResource(R.string.tasbih),
                    painterResource(id = R.drawable.tasbih),
                    "tasbihScreen"
                ),
                Triple(
                    stringResource(R.string.qiblah),
                    painterResource(id = R.drawable.qiblah),
                    "qiblahScreen"
                )
            ),
            navController = navController
        )

        DuaCard("رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = NavController(LocalContext.current)
    )
}