package com.bhatia.budgettracker.repository

import android.content.SharedPreferences
import android.text.format.DateFormat
import com.bhatia.budgettracker.db.AppDB
import com.bhatia.budgettracker.model.Balance
import com.bhatia.budgettracker.model.Transaction
import java.util.Date


class AppRepo(private val db: AppDB, private val sharedPreferences: SharedPreferences) {

    suspend fun addInitialBalanceIfNotExists() {
        val exists = db.getBalanceDao().checkIfExists()
        if (exists == 0) {
            val initialBalance = Balance(id = 1, availableBalance = 0.0)
            db.getBalanceDao().addBalance(initialBalance)
        }
    }

    suspend fun addTransaction(transaction: Transaction) =
        db.getTransactionDao().addTransaction(transaction)

    suspend fun editTransaction(transaction: Transaction) =
        db.getTransactionDao().editTransaction(transaction)

    suspend fun updateBalance(balance: Balance) = db.getBalanceDao().updateBalance(balance)


    suspend fun deleteTransaction(transaction: Transaction) =
        db.getTransactionDao().deleteTransaction(transaction.id)

    fun getBalance() = db.getBalanceDao().getBalance()


    fun getAllTransaction() = db.getTransactionDao().getAllTransactions()


    fun getLatestTransactions() = db.getTransactionDao().getLatestTransactions()


    fun searchTransactionByMonth(month: String) =
        db.getTransactionDao().searchTransactionByMonth(month)


    suspend fun deleteTransactionsOlderThanFourYear() {
        val currentYear = DateFormat.format("yyyy", Date()).toString().toInt()
        val previousYear = (currentYear - 4).toString()
        val months = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        for (month in months) {
            db.getTransactionDao().deleteOldTransactions(month, previousYear)
        }
    }

    fun getThemePreference(): Boolean {
        return sharedPreferences.getBoolean("theme_preference", false)
    }

    fun setThemePreference(isChecked: Boolean) {
        sharedPreferences.edit().putBoolean("theme_preference", isChecked).apply()
    }

    fun getThemeColor(): String {
        return sharedPreferences.getString("theme_color", "Blue").toString()
    }

    fun setThemeColor(isSelected: String) {
        sharedPreferences.edit().putString("theme_color", isSelected).apply()
    }


}