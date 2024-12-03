package com.bhatia.budgettracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bhatia.budgettracker.model.Balance
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBalance(balance: Balance)

    @Update
    suspend fun updateBalance(balance: Balance)

    @Query("SELECT COUNT(*) FROM balance WHERE id = 1")
    suspend fun checkIfExists(): Int

    @Query("SELECT * FROM balance WHERE id = 1")
    fun getBalance(): Flow<Balance>
}