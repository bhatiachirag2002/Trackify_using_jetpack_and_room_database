package com.bhatia.budgettracker.util

import java.text.DecimalFormat


object CurrencyFormatter {
    fun formatIndianCurrency(amount: Double): String {
        val format = DecimalFormat("#,##,##0.00")
        val formattedAmount = format.format(amount)
        return if (formattedAmount.endsWith(".00")) {
            formattedAmount.substring(0, formattedAmount.length - 3)
        } else {
            formattedAmount
        }
    }

    fun amountFormate(amount: Double): String {
        val format = DecimalFormat("#####0.00")
        val formattedAmount = format.format(amount)
        return if (formattedAmount.endsWith(".00")) {
            formattedAmount.substring(0, formattedAmount.length - 3)
        } else {
            formattedAmount
        }
    }
}