package com.bhatia.budgettracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhatia.budgettracker.model.Balance
import com.bhatia.budgettracker.model.Transaction
import com.bhatia.budgettracker.repository.AppRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(private val appRepo: AppRepo) : ViewModel() {

    // StateFlows to hold UI state, with explicit types
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList()) // Explicit type
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _monthsTransaction =
        MutableStateFlow<List<Transaction>>(emptyList()) // Explicit type
    val monthsTransaction: StateFlow<List<Transaction>> = _monthsTransaction.asStateFlow()

    private val _latestTransactions =
        MutableStateFlow<List<Transaction>>(emptyList()) // Explicit type
    val latestTransactions: StateFlow<List<Transaction>> = _latestTransactions.asStateFlow()

    private val _balance = MutableStateFlow<Balance?>(null) // Explicit type
    val balance: StateFlow<Balance?> = _balance.asStateFlow()

    private val _themeChecked = MutableStateFlow(appRepo.getThemePreference())
    val themeChecked: StateFlow<Boolean> get() = _themeChecked

    private val _colorSelected = MutableStateFlow(appRepo.getThemeColor())
    val colorSelected: StateFlow<String> get() = _colorSelected

    init {
        // Initialize balance and delete old transactions
        viewModelScope.launch {
            appRepo.addInitialBalanceIfNotExists()
            appRepo.deleteTransactionsOlderThanFourYear()
        }
        viewModelScope.launch {
            appRepo.getBalance().collect { balance: Balance? ->
                _balance.value = balance
            }
        }

        viewModelScope.launch {
            appRepo.getAllTransaction()
                .collect { transactions: List<Transaction> -> // Explicit type here
                    _transactions.value = transactions
                }
        }

        viewModelScope.launch {
            val isDark = appRepo.getThemePreference()
            updateThemePreference(isDark)
        }

        viewModelScope.launch {
            val color = appRepo.getThemeColor()
            updateThemeColor(color)
        }

    }

    fun updateBalance(balance: Balance) {
        viewModelScope.launch {
            appRepo.updateBalance(balance)
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            appRepo.addTransaction(transaction)
        }
    }

    fun editTransaction(transaction: Transaction) {
        viewModelScope.launch {
            appRepo.editTransaction(transaction)
        }
    }


    fun searchTransactionByMonth(month: String) {
        viewModelScope.launch {
            appRepo.searchTransactionByMonth(month)
                .collect { filteredTransactions: List<Transaction> -> // Explicit type here
                    _monthsTransaction.value = filteredTransactions
                }
        }
    }

    fun getLatestTransactions() {
        viewModelScope.launch {
            appRepo.getLatestTransactions()
                .collect { latest: List<Transaction> -> // Explicit type here
                    _latestTransactions.value = latest
                }
        }
    }


    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            appRepo.deleteTransaction(transaction)
        }
    }

    fun updateThemePreference(isChecked: Boolean) {
        appRepo.setThemePreference(isChecked)
        _themeChecked.value = isChecked
    }

    fun updateThemeColor(selected: String) {
        appRepo.setThemeColor(selected)
        _colorSelected.value = selected
    }
}
