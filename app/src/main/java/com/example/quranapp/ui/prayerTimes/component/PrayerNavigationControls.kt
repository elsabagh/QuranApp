package com.example.quranapp.ui.prayerTimes.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrayerNavigationControls(
    onNextClick: () -> Unit = {},
    onPreviousClick: () -> Unit = {},
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { onNextClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Next")
        }

        DatePicker(
            selectedDate = selectedDate, onDateSelected = onDateSelected,
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = { onPreviousClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Previous")
        }
    }
}

