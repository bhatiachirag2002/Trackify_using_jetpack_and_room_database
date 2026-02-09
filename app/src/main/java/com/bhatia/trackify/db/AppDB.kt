package com.bhatia.budgettracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bhatia.budgettracker.model.Balance
import com.bhatia.budgettracker.model.Transaction

@Database(entities = [Balance::class, Transaction::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {


    abstract fun getBalanceDao(): BalanceDao


    abstract fun getTransactionDao(): TransactionDao

    companion object {
        @Volatile
        private var instance: AppDB? = null
        private val LOCK = Any()  // Lock for thread-safe singleton access

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java,
                name = "budget_tracker_db"  // Name of the database file
            ).build()
    }
}