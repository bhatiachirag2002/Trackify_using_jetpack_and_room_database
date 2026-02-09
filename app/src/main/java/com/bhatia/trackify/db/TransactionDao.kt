package com.bhatia.budgettracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bhatia.budgettracker.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: Transaction)

    @Update
    suspend fun editTransaction(transaction: Transaction)

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransaction(transactionId: Int)

    @Query("SELECT * FROM transactions WHERE date LIKE '%' || :month || '%' ORDER BY timeStamp DESC, id DESC")
    fun searchTransactionByMonth(month: String): Flow<List<Transaction>> // Updated to Flow

    @Query("SELECT * FROM transactions ORDER BY timeStamp DESC , id DESC")
    fun getAllTransactions(): Flow<List<Transaction>> // Updated to Flow

    @Query("SELECT * FROM transactions ORDER BY timeStamp DESC , id DESC LIMIT 20")
    fun getLatestTransactions(): Flow<List<Transaction>> // Updated to Flow

    @Query("DELETE FROM transactions WHERE date LIKE '%' || :month || '%' AND date LIKE '%' || :year || '%'")
    suspend fun deleteOldTransactions(month: String, year: String)
}
