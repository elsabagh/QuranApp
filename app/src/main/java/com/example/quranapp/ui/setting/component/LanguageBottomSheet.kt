package com.example.quranapp.ui.setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quranapp.R
import com.example.quranapp.ui.setting.LanguageItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    languages: List<LanguageItem>,
    onLanguageSelected: (LanguageItem) -> Unit,
    onDismiss: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "اختر اللغة",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            languages.forEach { lang ->
                LanguageListItem(lang, onLanguageSelected)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLanguageBottomSheet() {
    val languages = listOf(
        LanguageItem("ar", R.drawable.flag_ar, "العربية"),
        LanguageItem("en", R.drawable.flag_en, "English")
    )
    LanguageBottomSheet(
        languages = languages,
        onLanguageSelected = {},
        onDismiss = {}
    )
}