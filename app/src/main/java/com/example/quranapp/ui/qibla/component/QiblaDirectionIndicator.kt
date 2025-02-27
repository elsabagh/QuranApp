package com.example.quranapp.ui.qibla.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quranapp.R

@Composable
fun QiblaDirectionIndicator(isFacingQibla: Boolean) {
    val icon = if (isFacingQibla) R.drawable.qiblaiconpoint else R.drawable.arrowup
    val size = if (isFacingQibla) 60.dp else 30.dp

    Icon(
        modifier = Modifier.size(size),
        painter = painterResource(id = icon),
        contentDescription = null
    )

}

@Preview(showBackground = true)
@Composable
fun PreviewQiblaDirectionIndicator() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        QiblaDirectionIndicator(isFacingQibla = false)
        QiblaDirectionIndicator(isFacingQibla = true)
    }
}

