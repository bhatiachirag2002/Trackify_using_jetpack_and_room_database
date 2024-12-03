package com.bhatia.budgettracker.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TransactionCategory(
    val name: String,
    val icon: ImageVector,
    val color: Color = Color.Red
)
