package com.bhatia.trackify.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bhatia.trackify.ui.theme.latoFamily
import com.bhatia.trackify.viewmodel.AppViewModel

@Composable
fun EmptyTransactionMessage(appViewModel: AppViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Transaction not found",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = latoFamily,
            color = whiteColor(appViewModel),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
