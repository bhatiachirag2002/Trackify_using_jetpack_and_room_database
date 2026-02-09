package com.bhatia.trackify.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowCircleDown
import androidx.compose.material.icons.rounded.ArrowCircleUp
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bhatia.trackify.ui.theme.latoFamily
import com.bhatia.trackify.util.CurrencyFormatter
import com.bhatia.trackify.util.EmptyTransactionMessage
import com.bhatia.trackify.util.TransactionColumn
import com.bhatia.trackify.util.UpdateBalance
import com.bhatia.trackify.util.background
import com.bhatia.trackify.util.blackColor
import com.bhatia.trackify.util.themeColor
import com.bhatia.trackify.util.themeLightColor
import com.bhatia.trackify.util.whiteColor
import com.bhatia.trackify.viewmodel.AppViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun HomeScreen(navController: NavController, appViewModel: AppViewModel) {
    val context = LocalContext.current
    appViewModel.getLatestTransactions()
    val filteredTransactions = appViewModel.latestTransactions.collectAsState(initial = emptyList())
    val balance = appViewModel.balance.collectAsState(initial = null)
    val white = whiteColor(appViewModel)
    val black = blackColor(appViewModel)
    Scaffold(containerColor = background(appViewModel)) {
        Column {
            Surface(
                color = background(appViewModel), modifier = Modifier
                    .fillMaxWidth()
                    .height(265.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .padding(bottom = 120.dp - it.calculateTopPadding())
                        .fillMaxWidth(),
                    shadowElevation = 10.dp,
                    color = themeLightColor(appViewModel),
                    shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = it.calculateTopPadding() + 10.dp)
                            .padding(horizontal = 10.dp)
                            .height(34.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        GreetingMessage()
                        Spacer(modifier = Modifier.weight(1f))
                        FloatingActionButton(
                            onClick = {
                                navController.navigate("Add")
                            },
                            shape = CircleShape,
                            containerColor = Color.White,
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                Icons.Sharp.Add,
                                contentDescription = "Add Transaction",
                                modifier = Modifier.size(28.dp),
                                tint = themeColor(appViewModel)
                            )
                        }
                    }

                }
                AvailableBalanceCard(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 60.dp + it.calculateTopPadding())
                        .height(160.dp)
                        .fillMaxWidth(), appViewModel = appViewModel
                )
            }

            if (filteredTransactions.value.isNotEmpty()) {
                TransactionColumn(
                    filteredTransactions = filteredTransactions.value.toMutableList(),
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
fun AvailableBalanceCard(modifier: Modifier = Modifier, appViewModel: AppViewModel) {
    val formatter = SimpleDateFormat("MMMM", Locale.getDefault())
    val currentMonth = formatter.format(System.currentTimeMillis())
    appViewModel.searchTransactionByMonth(currentMonth)
    val transactions = appViewModel.monthsTransaction.collectAsState(initial = emptyList())
    val balance = appViewModel.balance.collectAsState(initial = null)
    val income = transactions.value.filter { it.type == "Credit" }.sumOf { it.amount }
    val expense = transactions.value.filter { it.type == "Debit" }.sumOf { it.amount }

    Box {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = themeColor(appViewModel)),
            shape = RoundedCornerShape(10)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Available Balance",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = latoFamily,
                    letterSpacing = 0.3.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                val balanceValue = balance.value?.availableBalance ?: 0.0
                val balanceColor = Color.White
                Text(
                    text = "₹ ${CurrencyFormatter.formatIndianCurrency(balanceValue)}",
                    color = balanceColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = latoFamily,
                    letterSpacing = 0.3.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IncomeAndExpense(
                        modifier = Modifier.weight(1f),
                        label = "Income",
                        icon = Icons.Rounded.ArrowCircleUp,
                        amount = "₹ ${CurrencyFormatter.formatIndianCurrency(income)}"
                    )
                    Spacer(modifier = Modifier.width(20.dp)) // Add spacing between
                    IncomeAndExpense(
                        modifier = Modifier.weight(1f),
                        label = "Expense",
                        icon = Icons.Rounded.ArrowCircleDown,
                        amount = "₹ ${CurrencyFormatter.formatIndianCurrency(expense)}"
                    )
                }
            }
        }
    }
}

@Composable
fun IncomeAndExpense(
    modifier: Modifier = Modifier, label: String, icon: ImageVector, amount: String
) {
    Column(modifier = modifier.padding(top = 15.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon, contentDescription = null, Modifier.size(20.dp), tint = Color.White
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontFamily = latoFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = amount,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = latoFamily
        )
    }
}


@Composable
fun GreetingMessage() {
    val currentTime = remember { Calendar.getInstance().time }
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentHour = formatter.format(currentTime).split(":")[0].toInt()

    val greetingMessage = when (currentHour) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }
    Text(
        text = greetingMessage,
        color = Color.White,
        fontSize = 28.sp,
        fontFamily = latoFamily,
        fontWeight = FontWeight.Bold
    )
}
