package com.bhatia.budgettracker.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "balance")
@Parcelize
data class Balance(
    @PrimaryKey
    val id: Int = 1,
    val availableBalance: Double
) : Parcelable
