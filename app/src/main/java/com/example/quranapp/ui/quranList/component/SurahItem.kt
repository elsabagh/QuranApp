package com.example.quranapp.ui.quranList.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranapp.R
import com.example.quranapp.data.model.Surah

@Composable
fun SurahItem(
    surah: Surah,
    onClick: () -> Unit,
    onDownloadClick: () -> Unit,
    isDownloading: Boolean, // New parameter to track download state
    isDownloaded: Boolean // New parameter to track downloaded state
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Surah Number Icon
            Box(
                modifier = Modifier.size(46.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    modifier = Modifier.matchParentSize(),
                    contentDescription = null
                )
                Text(
                    text = "${surah.number}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = surah.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = surah.revelationType,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.2f))
                            .size(8.dp)
                    )

                    Text(
                        text = "${surah.numberOfAyahs} Ayahs",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            // Display appropriate icon
            when {
                isDownloading -> IconButton(
                    onClick = { /* Handle download button click */ },
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }

                isDownloaded -> {
                    /* Not Display download icon */
                }

                else -> IconButton(onClick = { onDownloadClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.progress_download),
                        contentDescription = "Download Surah"
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SurahItemPreview() {
    SurahItem(
        surah = Surah(
            ayahs = emptyList(),
            englishName = "Al-Fatiha",
            name = "الفاتحة",
            number = 1,
            revelationType = "Meccan",
            numberOfAyahs = 7
        ),
        onClick = {},
        onDownloadClick = {},
        isDownloading = false,
        isDownloaded = false
    )
}