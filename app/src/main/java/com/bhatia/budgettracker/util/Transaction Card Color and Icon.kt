package com.bhatia.budgettracker.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.bhatia.budgettracker.model.Transaction
import com.bhatia.budgettracker.ui.theme.TealGreen

fun getTypeColor(transaction: Transaction): Color {
    return if (transaction.type == "Credit") TealGreen else Color.Red
}

fun getCategoryIcon(transaction: Transaction): ImageVector {
    val categoryList =
        if (transaction.type == "Credit") TransactionData.creditCategories else TransactionData.debitCategories
    return categoryList.find { it.name == transaction.category }?.icon ?: Icons.Default.Category
}

fun getCategoryColor(transaction: Transaction): Color {
    val categoryList =
        if (transaction.type == "Credit") TransactionData.creditCategories else TransactionData.debitCategories
    return categoryList.find { it.name == transaction.category }?.color ?: Color.Gray
}

