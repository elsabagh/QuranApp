package com.example.quranapp.ui.prayerTimes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            label = { Text("المدينة") },
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        // اختيار الدولة
        OutlinedTextField(
            value = selectedCountry,
            onValueChange = onCountrySelected,
            label = { Text("الدولة") },
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
    }
}
