package com.bhatia.trackify.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhatia.trackify.model.Transaction
import com.bhatia.trackify.ui.theme.latoFamily
import com.bhatia.trackify.util.CurrencyFormatter
import com.bhatia.trackify.util.EmptyTransactionMessage
import com.bhatia.trackify.util.TransactionCard
import com.bhatia.trackify.util.TransactionChips
import com.bhatia.trackify.util.TransactionData
import com.bhatia.trackify.util.background
import com.bhatia.trackify.util.blackColor
import com.bhatia.trackify.util.dateFormater
import com.bhatia.trackify.util.getCategoryColor
import com.bhatia.trackify.util.getCategoryIcon
import com.bhatia.trackify.util.getEndOfYear
import com.bhatia.trackify.util.getStartOfYear
import com.bhatia.trackify.util.getTypeColor
import com.bhatia.trackify.util.monthFormater
import com.bhatia.trackify.util.whiteColor
import com.bhatia.trackify.viewmodel.AppViewModel
import java.util.Calendar


@Composable
fun StaticsScreen(appViewModel: AppViewModel) {
    Scaffold(containerColor = background(appViewModel)) {
        val selectedPeriod = remember { mutableStateOf("Today") }
        val selectedType = remember { mutableStateOf("Debit") }

        appViewModel.searchTransactionByMonth(monthFormater(System.currentTimeMillis()))
        val monthsTransaction = appViewModel.monthsTransaction.collectAsState(initial = emptyList())
        val allTransaction = appViewModel.transactions.collectAsState(initial = emptyList())

        val filteredTransactions = getFilteredTransactions(
            selectedPeriod.value,
            selectedType.value,
            allTransaction.value,
            monthsTransaction.value
        )
        val white = whiteColor(appViewModel)
        val black = blackColor(appViewModel)
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Surface(
                modifier = Modifier.height(58.dp),
                color = background(appViewModel = appViewModel)
            ) {
                PeriodSelection(
                    selectedPeriod = selectedPeriod.value,
                    bgColor = white,
                    textColor = black,
                    appViewModel = appViewModel,
                    onSelected = { period ->
                        selectedPeriod.value = period
                    })
            }
            Surface(
                modifier = Modifier.height(58.dp),
                color = background(appViewModel = appViewModel)
            ) {
                TransactionsTypeSelection(
                    selectedType = selectedType.value,
                    bgColor = white, textColor = black,
                    appViewModel = appViewModel,
                    onTypeSelected = { type ->
                        selectedType.value = type
                    })
            }

            if (filteredTransactions.isNotEmpty()) {
                TransactionList(
                    filteredTransactions = filteredTransactions,
                    white = white,
                    black = black,
                    selectedPeriod = selectedPeriod.value,
                    selectedType = selectedType.value,
                    appViewModel = appViewModel
                )
            } else {
                EmptyTransactionMessage(appViewModel)
            }
        }
    }
}

@Composable
fun TransactionList(
    filteredTransactions: List<Transaction>,
    white: Color,
    black: Color,
    selectedPeriod: String,
    selectedType: String,
    appViewModel: AppViewModel
) {
    val label = when (selectedPeriod) {
        "Today" -> "Today"
        "Month" -> "Monthly"
        "Year" -> "This Year"
        else -> "Last Year"
    }
    val type = if (selectedType == "Debit") "Expenses" else "Income"
    DonutChatScreen(
        filteredTransactions,
        label = "$label $type",
        textColor = white,
        appViewModel = appViewModel
    )
    LazyColumn {
        items(filteredTransactions.size) { index ->
            val transaction = filteredTransactions[index]
            val typeColor = getTypeColor(transaction)
            val categoryColor = getCategoryColor(transaction)
            val categoryIcon = getCategoryIcon(transaction)
            val formattedDate = dateFormater(transaction.timeStamp)

            TransactionCard(
                icon = categoryIcon,
                typeColor = typeColor,
                title = transaction.title,
                date = formattedDate,
                amount = transaction.amount,
                backgroundColor = categoryColor,
                cardColors = black,
                textColor = white,
                modifier = if (index == filteredTransactions.size - 1) Modifier.padding(bottom = 75.dp) else Modifier
            )
        }
    }
}

@Composable
fun PeriodSelection(
    selectedPeriod: String,
    onSelected: (String) -> Unit,
    bgColor: Color,
    textColor: Color,
    appViewModel: AppViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(start = 3.dp)
    ) {
        TransactionChips(
            text = "Today",
            isSelected = "Today" == selectedPeriod,
            onClick = { onSelected("Today") },
            modifier = Modifier.weight(1f),
            bgColor = bgColor,
            textColor = textColor,
            appViewModel = appViewModel
        )
        Spacer(modifier = Modifier.width(3.dp))
        TransactionChips(
            text = "Month",
            isSelected = "Month" == selectedPeriod,
            onClick = { onSelected("Month") },
            modifier = Modifier.weight(1f),
            bgColor = bgColor,
            textColor = textColor,
            appViewModel = appViewModel
        )
        Spacer(modifier = Modifier.width(3.dp))
        TransactionChips(
            text = "Year",
            isSelected = "Year" == selectedPeriod,
            onClick = { onSelected("Year") },
            modifier = Modifier.weight(1f),
            bgColor = bgColor,
            textColor = textColor,
            appViewModel = appViewModel
        )
        TransactionChips(
            text = "Last Year",
            isSelected = "Last Year" == selectedPeriod,
            onClick = { onSelected("Last Year") },
            modifier = Modifier.weight(1f),
            bgColor = bgColor,
            textColor = textColor,
            appViewModel = appViewModel
        )
    }

}

@Composable
fun TransactionsTypeSelection(
    selectedType: String,
    onTypeSelected: (String) -> Unit,
    bgColor: Color,
    textColor: Color,
    appViewModel: AppViewModel
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(start = 3.dp)
    ) {
        Spacer(modifier = Modifier.width(3.dp))
        TransactionChips(
            text = "Debit",
            isSelected = "Debit" == selectedType,
            onClick = { onTypeSelected("Debit") },
            modifier = Modifier.weight(1f), bgColor = bgColor, textColor = textColor,
            appViewModel = appViewModel
        )
        Spacer(modifier = Modifier.width(3.dp))
        TransactionChips(
            text = "Credit",
            isSelected = "Credit" == selectedType,
            onClick = { onTypeSelected("Credit") },
            modifier = Modifier.weight(1f), bgColor = bgColor, textColor = textColor,
            appViewModel = appViewModel
        )
    }

}

@Composable
fun DonutChart(
    data: Map<String, Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    total: Float = 100f
) {
    val sweepAngles = data.values.map { (it / total) * 360f }

    Canvas(modifier = modifier.size(200.dp)) {
        var startAngle = -100f // Start from top
        sweepAngles.forEachIndexed { index, sweepAngle ->
            drawArc(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 40.dp.toPx(), cap = StrokeCap.Butt)
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
fun DonutChartWithLabels(
    data: Map<String, Float>,
    colors: List<Color>,
    amount: Double,
    textColor: Color
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        DonutChart(data = data, colors = colors)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Total",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = textColor,
                fontFamily = latoFamily
            )
            Text(
                "₹${CurrencyFormatter.formatIndianCurrency(amount)}",
                fontSize = 14.sp,
                color = textColor,
                fontFamily = latoFamily
            )
        }
    }
}

@Composable
fun DonutChatScreen(
    filteredTransactions: List<Transaction>,
    label: String,
    textColor: Color,
    appViewModel: AppViewModel
) {
    val categoryTotals = filteredTransactions.groupBy { it.category }.mapValues { entry ->
        entry.value.sumOf { it.amount } // Ensure `amount` is a numeric type
    }

    val totalAmount = categoryTotals.values.sum() // Works if `categoryTotals.values` are numeric
    val categoryPercentages =
        categoryTotals.mapValues { (it.value.toFloat() / totalAmount.toFloat()) * 100 }


    Column(
        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = latoFamily,
            color = textColor,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
        )

        val categoryColors = categoryPercentages.keys.map { category ->
            TransactionData.getCategoriesColors(category) ?: Color.Gray
        }

        Surface(
            color = background(appViewModel = appViewModel),
            modifier = Modifier
                .size(250.dp)
                .padding(5.dp)
        ) {
            DonutChartWithLabels(
                data = categoryPercentages,
                colors = categoryColors,
                amount = categoryTotals.values.sum(),
                textColor = textColor
            )
        }
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(categoryPercentages.entries.toList()) { (category, percentage) -> // Use `entries` for key-value pairs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(
                                color = categoryColors[categoryPercentages.keys.indexOf(category)],
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "$category: ${percentage.toInt()}%",
                        fontSize = 14.sp,
                        fontFamily = latoFamily,
                        color = textColor
                    )
                }
            }
        }
    }
}


fun getFilteredTransactions(
    period: String,
    type: String,
    allTransactions: List<Transaction>,
    monthTransactions: List<Transaction>
): List<Transaction> {
    val filteredPeriodTransactions = when (period) {
        "Today" -> allTransactions.filter { it.date == dateFormater(System.currentTimeMillis()) }
        "Month" -> monthTransactions
        "Year" -> filterByFinancialYear(allTransactions)
        else -> filterByPreviousFinancialYear(allTransactions)
    }
    return filteredPeriodTransactions.filter { it.type == type }
}


fun filterByFinancialYear(allTransactions: List<Transaction>): List<Transaction> {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val financialYearStart = getStartOfYear(currentYear)
    val financialYearEnd = getEndOfYear(currentYear + 1)
    return allTransactions.filter { transaction -> transaction.timeStamp in financialYearStart..financialYearEnd }
}

fun filterByPreviousFinancialYear(allTransactions: List<Transaction>): List<Transaction> {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val financialYearStart = getStartOfYear(currentYear - 1)
    val financialYearEnd = getEndOfYear(currentYear)
    return allTransactions.filter { transaction -> transaction.timeStamp in financialYearStart..financialYearEnd }
}


