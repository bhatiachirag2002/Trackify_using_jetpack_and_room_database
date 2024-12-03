package com.bhatia.budgettracker.util

import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import com.bhatia.budgettracker.model.Transaction
import com.bhatia.budgettracker.ui.theme.TealGreen
import com.bhatia.budgettracker.ui.theme.latoFamily

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
    val transactionsState =
        remember { mutableStateListOf<Transaction>().apply { addAll(filteredTransactions) } }
    val selectedTransaction = remember { mutableStateOf<Transaction?>(null) }

    LazyColumn {
        items(transactionsState.size) { index ->
            val transaction = transactionsState[index]
            val typeColor = getTypeColor(transaction)
            val categoryColor = getCategoryColor(transaction)
            val categoryIcon = getCategoryIcon(transaction)
            val formattedDate = dateFormater(transaction.timeStamp)
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    when (it) {
                        SwipeToDismissBoxValue.StartToEnd -> {
                            selectedTransaction.value = transaction
                            onEditTransaction(selectedTransaction.value!!)
                            false
                        }

                        SwipeToDismissBoxValue.EndToStart -> {
                            selectedTransaction.value = transaction
                            onDeleteTransaction(selectedTransaction.value!!)
                            transactionsState.remove(selectedTransaction.value)
                            false
                        }

                        else -> false
                    }
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    // Safely check the target value for determining swipe direction
                    val isSwipingToStart =
                        dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd
                    val isSwipingToEnd =
                        dismissState.targetValue == SwipeToDismissBoxValue.EndToStart


                    when {
                        isSwipingToStart -> {
                            BackgroundCard(
                                bgColor = TealGreen,
                                icon = Icons.Default.Edit,
                                arrangement = Arrangement.Start
                            )
                        }

                        isSwipingToEnd -> {
                            BackgroundCard(
                                bgColor = Color.Red,
                                icon = Icons.Default.DeleteForever,
                                arrangement = Arrangement.End
                            )
                        }

                        else -> Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(horizontal = 6.dp, vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(TealGreen)
                                ) {}
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(Color.Red)
                                ) {}
                            }
                        }
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
                        modifier = if (index == filteredTransactions.size - 1) Modifier.padding(
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
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 7.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = arrangement
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )

        }
    }
}