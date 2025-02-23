package com.example.quranapp.ui.prayerTimes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quranapp.ui.prayerTimes.component.AddressPicker
import com.example.quranapp.ui.prayerTimes.component.PrayerNavigationControls
import com.example.quranapp.ui.prayerTimes.component.PrayerTimesList
import com.example.quranapp.ui.prayerTimes.component.PrayerTopBar
import com.example.quranapp.util.FormatDate
import com.google.android.gms.location.LocationServices

@Composable
fun PrayerTimesByDateScreen(
    navController: NavController,
    onBackClick: () -> Unit
) {
    val viewModel: PrayerTimesByDateViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val selectedAddress by viewModel.selectedAddress.collectAsState()

    var selectedDate by remember { mutableStateOf(FormatDate.getTodayDate()) }
    var city by remember { mutableStateOf(selectedAddress.substringBefore(",")) }
    var country by remember { mutableStateOf(selectedAddress.substringAfter(", ")) }
    val context = LocalContext.current

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val updatedAddress by rememberUpdatedState(selectedAddress)

    // تحديث الموقع تلقائياً عند فتح الصفحة
    LaunchedEffect(Unit) {
        viewModel.fetchLocationAutomatically(fusedLocationClient, context)
    }

    // تحديث القيم عند تغير العنوان المختار
    LaunchedEffect(updatedAddress) {
        city = updatedAddress.substringBefore(",")
        country = updatedAddress.substringAfter(", ")
    }

    // تحديث الأوقات عند تغير التاريخ أو الموقع
    LaunchedEffect(selectedDate, city, country) {
        val newAddress = "$city, $country"
        viewModel.fetchPrayerTimes(newAddress, selectedDate)
    }

    Scaffold(
        topBar = {
            PrayerTopBar(
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(paddingValues)
        ) {
            AddressPicker(
                selectedCity = city,
                selectedCountry = country,
                onCitySelected = { newCity ->
                    city = newCity
                    viewModel.updateAddress("$newCity, $country")
                },
                onCountrySelected = { newCountry ->
                    country = newCountry
                    viewModel.updateAddress("$city, $newCountry")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrayerNavigationControls(
                onNextClick = { selectedDate = FormatDate.getNextDate(selectedDate) },
                onPreviousClick = { selectedDate = FormatDate.getPreviousDate(selectedDate) },
                selectedDate = selectedDate,
                onDateSelected = { date ->
                    selectedDate = date
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (uiState) {
                is PrayerTimesUiState.Loading -> CircularProgressIndicator()
                is PrayerTimesUiState.Success -> PrayerTimesList((uiState as PrayerTimesUiState.Success).data)
                is PrayerTimesUiState.Error -> Text(
                    text = (uiState as PrayerTimesUiState.Error).message,
                    color = Color.Red
                )
            }
        }
    }
}
