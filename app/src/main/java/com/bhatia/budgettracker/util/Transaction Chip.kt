package com.bhatia.budgettracker.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhatia.budgettracker.viewmodel.AppViewModel

@Composable
fun TransactionChips(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color,
    bgColor: Color,
    appViewModel: AppViewModel
) {

    Surface(
        shadowElevation = 5.dp,
        modifier = modifier
            .padding(horizontal = 3.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        color = if (isSelected) themeColor(appViewModel) else bgColor,
        shape = RoundedCornerShape(20),
    ) {
        Text(
            text,
            color = if (isSelected) Color.White else {
                textColor
            },
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = if (isSelected) 10.dp else 6.dp),
            textAlign = TextAlign.Center
        )

    }
}

