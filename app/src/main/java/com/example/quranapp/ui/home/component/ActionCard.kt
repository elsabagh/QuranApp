package com.example.quranapp.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ActionCard(
    label: String,
    icon: Painter,
    navController: NavController,
    route: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .clickable {
                navController.navigate(route)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(32.dp),
                painter = icon, contentDescription = label)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ActionRow(actions: List<Triple<String, Painter, String>>, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        actions.forEach { (title, icon, route) ->
            ActionCard(
                title, icon,
                navController,
                route, modifier = Modifier.weight(1f)
            )
        }
    }
}
