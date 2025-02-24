package com.example.quranapp.ui.masbaha.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quranapp.ui.theme.QuranAppTheme
import com.example.quranapp.ui.theme.TypographyCustom

@Composable
fun TasbihNameCard(
    name: String,
    count: Int,
    isSelected: Boolean, // هل هذه البطاقة هي المختارة؟
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            2.dp,
            if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        )

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = TypographyCustom.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black

            )
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(
                        MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(
                            4.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$count",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = TypographyCustom.bodySmall
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TasbihNameCardPreview() {
    QuranAppTheme {
        TasbihNameCard(
            name = "سبحان الله",
            count = 10,
            isSelected = false,
            onClick = {}
        )
    }
}