package com.example.quranapp.ui.prayerTimes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.quranapp.R

@Composable
fun AddressPicker(
    selectedCity: String,
    selectedCountry: String,
    onCitySelected: (String) -> Unit,
    onCountrySelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // اختيار المدينة
        OutlinedTextField(
            value = selectedCity,
            onValueChange = onCitySelected,
            label = { Text(stringResource(R.string.city)) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        // اختيار الدولة
        OutlinedTextField(
            value = selectedCountry,
            onValueChange = onCountrySelected,
            label = { Text(stringResource(R.string.country)) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}
