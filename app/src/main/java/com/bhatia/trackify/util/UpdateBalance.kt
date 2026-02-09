package com.bhatia.trackify.util

import com.bhatia.trackify.model.Balance
import com.bhatia.trackify.viewmodel.AppViewModel

object UpdateBalance {

    fun addUpdateBalance(
        amount: String,
        selectedType: String,
        appViewModel: AppViewModel,
        balance: Balance?
    ) {
        val transactionAmount = amount.toDoubleOrNull() ?: 0.0
        val currentBalance = balance?.availableBalance ?: 0.0

        val updatedBalance = when (selectedType) {
            "Debit" -> currentBalance - transactionAmount
            "Credit" -> currentBalance + transactionAmount
            else -> currentBalance
        }
        updateBalanceInViewModel(updatedBalance, appViewModel)
    }

    fun deleteUpdateBalance(
        amount: String,
        transactionType: String,
        appViewModel: AppViewModel,
        balance: Balance?
    ) {
        val transactionAmount = amount.toDoubleOrNull() ?: 0.0
        val currentBalance = balance?.availableBalance ?: 0.0

        val updatedBalance = when (transactionType) {
            "Debit" -> currentBalance + transactionAmount
            "Credit" -> currentBalance - transactionAmount
            else -> currentBalance
        }
        updateBalanceInViewModel(updatedBalance, appViewModel)
    }

    fun editUpdateBalance(
        oldAmount: String,
        newAmount: String,
        selectedType: String,
        appViewModel: AppViewModel,
        balance: Balance?
    ) {
        val oldTransactionAmount = oldAmount.toDoubleOrNull() ?: 0.0
        val newTransactionAmount = newAmount.toDoubleOrNull() ?: 0.0
        val currentBalance = balance?.availableBalance ?: 0.0
        val difference = newTransactionAmount - oldTransactionAmount

        val updatedBalance = when (selectedType) {
            "Debit" -> currentBalance - difference
            "Credit" -> currentBalance + difference
            else -> currentBalance
        }
        updateBalanceInViewModel(updatedBalance, appViewModel)
    }

    private fun updateBalanceInViewModel(updatedBalance: Double, appViewModel: AppViewModel) {
        val balance = Balance(id = 1, availableBalance = updatedBalance)
        appViewModel.updateBalance(balance)
    }
}