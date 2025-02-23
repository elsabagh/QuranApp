package com.example.quranapp.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.quranapp.ui.navigation.AppDestination

@Composable
fun BottomNavBar(
    bottomNavController: NavHostController,
) {

    val items = listOf(
        BottomItem(
            title = "Home",
            icon = Icons.Rounded.Home
        ),
        BottomItem(
            title = "Quran",
            icon = Icons.Rounded.MenuBook
        ), BottomItem(
            title = "Settings",
            icon = Icons.Rounded.Settings
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // ✅ شريط الخط الأسود فوق الأيقونات داخل NavigationBar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .weight(1f)
                            .height(4.dp)
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomStart = 8.dp,
                                    bottomEnd = 8.dp,
                                )
                            )
                            .background(if (selected.intValue == index) Color.Black else Color.Transparent)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEachIndexed { index, bottomItem ->
                    NavigationBarItem(
                        selected = selected.intValue == index,
                        onClick = {
                            selected.intValue = index
                            when (selected.intValue) {
                                0 -> {
                                    bottomNavController.popBackStack()
                                    bottomNavController.navigate(
                                        AppDestination.QuranHomeDestination.route
                                    )
                                }

                                1 -> {
                                    bottomNavController.popBackStack()
                                    bottomNavController.navigate(
                                        AppDestination.QuranPageDestination.route
                                    )
                                }

                                2 -> {
                                    bottomNavController.popBackStack()
                                    bottomNavController.navigate(
                                        AppDestination.PrayerTimesDestination.route
                                    )
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = bottomItem.icon,
                                contentDescription = bottomItem.title,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        label = {
                            Text(
                                text = bottomItem.title,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent // ✅ إلغاء الخلفية الدائرية عند التحديد
                        )
                    )
                }
            }
        }
    }
}

data class BottomItem(
    val title: String, val icon: ImageVector
)

@Preview()
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavBar(
        NavHostController(LocalContext.current)
    )
}

