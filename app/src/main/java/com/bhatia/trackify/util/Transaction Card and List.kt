package com.bhatia.trackify.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhatia.trackify.model.Transaction
import com.bhatia.trackify.ui.theme.TealGreen
import com.bhatia.trackify.ui.theme.latoFamily

@Composable
fun TransactionCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    typeColor: Color,
    backgroundColor: Color,
    title: String,
    date: String,
    amount: Double,
    cardColors: Color,
    textColor: Color,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColors),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = backgroundColor,
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(60)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(5.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = latoFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
                Text(
                    text = date,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    fontFamily = latoFamily,
                    fontStyle = FontStyle.Italic,
                    color = textColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "₹ ${CurrencyFormatter.formatIndianCurrency(amount)}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = latoFamily,
                color = typeColor
            )
        }
    }
}

@Composable
fun TransactionColumn(
    filteredTransactions: List<Transaction>,
    white: Color,
    black: Color,
    onDeleteTransaction: (Transaction) -> Unit,
    onEditTransaction: (Transaction) -> Unit
) {
    val transactionsState = remember { mutableStateListOf<Transaction>() }

    LaunchedEffect(filteredTransactions) {
        transactionsState.clear()
        transactionsState.addAll(filteredTransactions)
    }

    LazyColumn {
        itemsIndexed(
            items = transactionsState,
            key = { _, transaction -> transaction.id }
        ) { index, transaction ->
            val typeColor = getTypeColor(transaction)
            val categoryColor = getCategoryColor(transaction)
            val categoryIcon = getCategoryIcon(transaction)
            val formattedDate = dateFormater(transaction.timeStamp)

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    when (value) {
                        SwipeToDismissBoxValue.EndToStart -> {
                            onDeleteTransaction(transaction)
                            true
                        }
                        SwipeToDismissBoxValue.StartToEnd -> {
                            false
                        }
                        else -> false
                    }
                }
            )


            LaunchedEffect(dismissState.targetValue) {
                if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) {
                    onEditTransaction(transaction)
                    dismissState.reset()
                }
            }

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    val direction = dismissState.dismissDirection
                    when (direction) {
                        SwipeToDismissBoxValue.StartToEnd -> {
                            BackgroundCard(
                                bgColor = TealGreen,
                                icon = Icons.Default.Edit,
                                arrangement = Arrangement.Start
                            )
                        }
                        SwipeToDismissBoxValue.EndToStart -> {
                            BackgroundCard(
                                bgColor = Color.Red,
                                icon = Icons.Default.DeleteForever,
                                arrangement = Arrangement.End
                            )
                        }
                        else -> Box(Modifier.fillMaxSize())
                    }
                },
                content = {
                    TransactionCard(
                        icon = categoryIcon,
                        typeColor = typeColor,
                        title = transaction.title,
                        date = formattedDate,
                        amount = transaction.amount,
                        backgroundColor = categoryColor,
                        cardColors = black,
                        textColor = white,
                        modifier = if (index == transactionsState.size - 1) Modifier.padding(
                            bottom = 75.dp
                        ) else Modifier
                    )
                }
            )
        }
    }
}

@Composable
fun BackgroundCard(bgColor: Color, icon: ImageVector, arrangement: Arrangement.Horizontal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp) // Thoda extra padding icon ke liye
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = arrangement
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}