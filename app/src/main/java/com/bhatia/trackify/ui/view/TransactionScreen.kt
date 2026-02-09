package com.bhatia.trackify.ui.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bhatia.trackify.model.Transaction
import com.bhatia.trackify.util.EmptyTransactionMessage
import com.bhatia.trackify.util.TransactionChips
import com.bhatia.trackify.util.TransactionColumn
import com.bhatia.trackify.util.UpdateBalance
import com.bhatia.trackify.util.background
import com.bhatia.trackify.util.blackColor
import com.bhatia.trackify.util.themeColor
import com.bhatia.trackify.util.whiteColor
import com.bhatia.trackify.viewmodel.AppViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun TransactionScreen(navController: NavController, appViewModel: AppViewModel) {
    val context = LocalContext.current
    val selectedMonth = remember { mutableStateOf("") }
    val selectedType = remember { mutableStateOf("") }

    val white = whiteColor(appViewModel)
    val black = blackColor(appViewModel)

    appViewModel.searchTransactionByMonth(selectedMonth.value)
    val monthsTransaction = appViewModel.monthsTransaction.collectAsState(initial = emptyList())
    val allTransaction = appViewModel.transactions.collectAsState(initial = emptyList())
    val balance = appViewModel.balance.collectAsState(initial = null)

    val filteredTransactions = getMonthFilteredTransactions(
        selectedMonth.value,
        selectedType.value,
        allTransaction.value,
        monthsTransaction.value
    )

    Scaffold(containerColor = background(appViewModel)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding())
        ) {
            Surface(modifier = Modifier.height(58.dp), color = background(appViewModel)) {
                MonthSelection(selectedMonth = selectedMonth.value,
                    bgColor = white,
                    textColor = black,
                    appViewModel = appViewModel,
                    onClick = { selectedMonth.value = "" },
                    onMonthSelected = { month -> selectedMonth.value = month })
            }
            Surface(modifier = Modifier.height(58.dp), color = background(appViewModel)) {
                TransactionTypeSelection(selectedType = selectedType.value,
                    onClick = { selectedType.value = "" },
                    bgColor = white,
                    textColor = black,
                    appViewModel = appViewModel,
                    onTypeSelected = { type -> selectedType.value = type })

            }
            if (filteredTransactions.isNotEmpty()) {
                TransactionColumn(
                    filteredTransactions = filteredTransactions.toMutableList(),
                    white = white,
                    black = black,
                    onDeleteTransaction = { transactionToDelete ->
                        appViewModel.deleteTransaction(transactionToDelete)
                        UpdateBalance.deleteUpdateBalance(
                            transactionToDelete.amount.toString(),
                            transactionToDelete.type,
                            appViewModel,
                            balance.value
                        )
                        Toast.makeText(context, "Deleted Transaction", Toast.LENGTH_SHORT).show()
                    },
                    onEditTransaction = { transactionToEdit ->
                        navController.navigate("Edit?id=${transactionToEdit.id}&title=${transactionToEdit.title}&amount=${transactionToEdit.amount}&type=${transactionToEdit.type}&timeStamp=${transactionToEdit.timeStamp}&category=${transactionToEdit.category}")
                    }
                )
            } else {
                EmptyTransactionMessage(appViewModel)
            }
        }
    }
}


@Composable
fun MonthSelection(
    selectedMonth: String,
    onMonthSelected: (String) -> Unit,
    onClick: () -> Unit,
    bgColor: Color,
    textColor: Color,
    appViewModel: AppViewModel

) {
    val months = generateMonthsList(36)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(start = 3.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(35.dp)
                .clickable(onClick = onClick),
            color = if ("" == selectedMonth) themeColor(appViewModel) else bgColor,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Rounded.GridView,
                contentDescription = null,
                tint = if ("" == selectedMonth) Color.White else {
                    textColor
                },
                modifier = Modifier.padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.width(3.dp))
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(months.size) { month ->
                MonthsChips(text = months[month],
                    isSelected = months[month] == selectedMonth,
                    bgColor = bgColor,
                    textColor = textColor,
                    appViewModel = appViewModel,
                    onClick = { onMonthSelected(months[month]) })
            }
        }
    }


}

@Composable
fun TransactionTypeSelection(
    selectedType: String,
    onTypeSelected: (String) -> Unit,
    onClick: () -> Unit,
    bgColor: Color,
    textColor: Color,
    appViewModel: AppViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(start = 3.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(35.dp)
                .clickable(onClick = onClick),
            color = if ("" == selectedType) themeColor(appViewModel) else bgColor,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Rounded.GridView,
                contentDescription = null,
                tint = if ("" == selectedType) Color.White else {
                    textColor
                },
                modifier = Modifier.padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.width(3.dp))
        TransactionChips(
            text = "Debit",
            isSelected = "Debit" == selectedType,
            onClick = { onTypeSelected("Debit") },
            modifier = Modifier.weight(1f),
            bgColor = bgColor,
            textColor = textColor,
            appViewModel = appViewModel
        )
        Spacer(modifier = Modifier.width(3.dp))
        TransactionChips(
            text = "Credit",
            isSelected = "Credit" == selectedType,
            onClick = { onTypeSelected("Credit") },
            modifier = Modifier.weight(1f),
            bgColor = bgColor,
            textColor = textColor,
            appViewModel = appViewModel
        )
    }

}

fun generateMonthsList(count: Int): List<String> {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val months = mutableListOf<String>()

    for (i in 0 until count) {
        months.add(dateFormat.format(calendar.time))
        calendar.add(Calendar.MONTH, -1)
    }
    return months
}


fun getMonthFilteredTransactions(
    month: String,
    type: String,
    allTransactions: List<Transaction>,
    monthTransactions: List<Transaction>
): List<Transaction> {
    val filteredPeriodTransactions = when (month) {
        "" -> allTransactions
        else -> monthTransactions
    }
    return if (type == "") filteredPeriodTransactions else filteredPeriodTransactions.filter { it.type == type }
}

@Composable
fun MonthsChips(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    textColor: Color,
    bgColor: Color,
    appViewModel: AppViewModel
) {
    Surface(
        shadowElevation = 5.dp,
        modifier = Modifier
            .padding(horizontal = 3.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        color = if (isSelected) themeColor(appViewModel) else bgColor,
        shape = RoundedCornerShape(20),
    ) {
        Text(
            text,
            color = if (isSelected) Color.White else {
                textColor
            },
            fontSize = 16.sp,
            modifier = Modifier.padding(
                vertical = if (isSelected) 10.dp else 6.dp,
                horizontal = 5.dp
            )
        )

    }
}