package com.example.quranapp.ui.quranList.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quranapp.ui.quranList.QuranFilterType

@Composable
fun QuranFilterBar(
    selectedFilter: QuranFilterType,
    onFilterSelected: (QuranFilterType) -> Unit
) {
    val filters = listOf(QuranFilterType.ALL, QuranFilterType.DOWNLOAD, //QuranFilterType.BOOKMARK
         )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        filters.forEachIndexed { index, filter ->
            val isSelected = selectedFilter == filter
            val isFirst = index == 0
            val isLast = index == filters.lastIndex

            Button(
                onClick = { onFilterSelected(filter) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color.DarkGray else Color.LightGray,
                    contentColor = if (isSelected) Color.White else Color.Black
                ),
                shape = when {
                    isFirst -> RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                    isLast -> RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                    else -> RoundedCornerShape(0.dp)
                },
                border = when {
                    isFirst -> BorderStroke(1.dp, Color.DarkGray)
                    isLast -> BorderStroke(1.dp, Color.DarkGray)
                    else -> BorderStroke(0.dp, Color.Transparent)
                },
                modifier = Modifier.weight(1f).height(
                    48.dp
                )
            ) {
                Text(text = filter.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuranFilterBarPreview() {
    QuranFilterBar(selectedFilter = QuranFilterType.ALL, onFilterSelected = {})
}
