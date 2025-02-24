package com.example.quranapp.ui.masbaha

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quranapp.ui.masbaha.component.MasbahaProgress
import com.example.quranapp.ui.masbaha.component.TasbihNameCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasbahaScreen(
    navController: NavController,
    onBackClick: () -> Unit = {}
) {
    val viewModel = hiltViewModel<MasbahaViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Masbaha"
                    )
                },
                navigationIcon = {
                    // ✅ زر الرجوع
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ✅ عرض العنصر الرئيسي
            MasbahaContent(
                uiState = uiState,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun MasbahaContent(
    uiState: MasbahaUiState,
    viewModel: MasbahaViewModel
) {
    val tasbihList = listOf(
        TasbihItem(id = 1, name = "سبحان الله", count = 33),
        TasbihItem(id = 2, name = "الحمد لله", count = 33),
        TasbihItem(id = 3, name = "الله أكبر", count = 34),
        TasbihItem(id = 4, name = "لا إله إلا الله", count = 100)
    )

    val selectedTasbih = tasbihList.find { it.id == uiState.selectedTasbihId }
    val maxCount = selectedTasbih?.count ?: 33 // القيم الافتراضية إذا لم يتم تحديد شيء

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MasbahaProgress(
            counterClick = uiState.counterClick,
            maxCount = maxCount, // ✅ تمرير العدد الأقصى المختار
            progress = uiState.progress, // ✅ حساب التقدم بناءً على maxCount
            onCounterClick = { viewModel.onCounterClick() }, // تحديث الحالة عبر ViewModel
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(46.dp))

        tasbihList.forEach { tasbih ->
            TasbihNameCard(
                name = tasbih.name,
                count = tasbih.count,
                isSelected = uiState.selectedTasbihId == tasbih.id, // ✅ مقارنة ID فقط
                onClick = { viewModel.onTasbihSelected(tasbih.id) }, // ✅ تغيير المحدد عند الضغط
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 8.dp)
            )
        }
    }
}