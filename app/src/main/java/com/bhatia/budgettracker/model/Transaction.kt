package com.bhatia.budgettracker.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "transactions")
@Parcelize
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var type: String,
    var title: String,
    var category: String,
    var amount: Double,
    var timeStamp: Long,
    var date: String
) : Parcelable
