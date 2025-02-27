package com.example.quranapp.ui.setting


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quranapp.R
import com.example.quranapp.ui.setting.component.LanguageBottomSheet
import com.example.quranapp.ui.setting.component.LanguageSelectionButton
import com.example.quranapp.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    onBackClick: () -> Unit
) {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val languageResource by viewModel.language.collectAsState()
    val context = LocalContext.current

    val languages = listOf(
        LanguageItem("ar", R.drawable.flag_ar, "العربية"),
        LanguageItem("en", R.drawable.flag_en, "English")
    )

    var showBottomSheet by remember { mutableStateOf(false) }

    // إجبار الشاشة على إعادة البناء عند تغيير اللغة
    languageResource.let { it ->
        when (it) {
            is Resource.Success -> {
                val selectedLanguageCode = it.data
                key(selectedLanguageCode) { // عند تغيير اللغة، يتم إعادة بناء الشاشة
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = stringResource(R.string.settings)) },
                                navigationIcon = {
                                    IconButton(onClick = { onBackClick() }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val selectedLanguage =
                                languages.find { it.code == selectedLanguageCode } ?: languages[0]

                            LanguageSelectionButton(
                                selectedLanguage = selectedLanguage,
                                onClick = { showBottomSheet = true }
                            )

                            if (showBottomSheet) {
                                LanguageBottomSheet(
                                    languages = languages,
                                    onLanguageSelected = { lang ->
                                        viewModel.changeLanguage(lang.code, context)
                                        showBottomSheet = false
                                    },
                                    onDismiss = { showBottomSheet = false }
                                )
                            }
                        }
                    }
                }
            }

            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Error -> {
                Text(text = "خطأ: ${it.message}")
            }
        }
    }
}
