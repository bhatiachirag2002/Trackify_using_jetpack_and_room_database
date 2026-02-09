@file:Suppress("UNUSED_EXPRESSION")

package com.bhatia.trackify.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bhatia.trackify.model.Transaction
import com.bhatia.trackify.ui.theme.latoFamily
import com.bhatia.trackify.util.CurrencyFormatter
import com.bhatia.trackify.util.DatePickerDropdown
import com.bhatia.trackify.util.OutlineTextField
import com.bhatia.trackify.util.TransactionData.creditCategories
import com.bhatia.trackify.util.TransactionData.debitCategories
import com.bhatia.trackify.util.UpdateBalance
import com.bhatia.trackify.util.background
import com.bhatia.trackify.util.blackColor
import com.bhatia.trackify.util.dateFormater
import com.bhatia.trackify.util.themeColor
import com.bhatia.trackify.util.whiteColor
import com.bhatia.trackify.viewmodel.AppViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditTransactionScreen(
    navController: NavController,
    appViewModel: AppViewModel,
    title: String,
    amount: Double,
    category: String,
    type: String,
    timeStamp: Long,
    id: Int
) {
    val balance = appViewModel.balance.collectAsState(initial = null)
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val white = whiteColor(appViewModel)
    val black = blackColor(appViewModel)
    val edtTitle = remember { mutableStateOf(title) }
    val edtAmount = remember { mutableStateOf(CurrencyFormatter.amountFormate(amount)) }
    val selectedCategory = remember { mutableStateOf(category) }
    val selectedType = remember { mutableStateOf(type) }
    val selectedDate = remember {
        mutableLongStateOf(
            timeStamp
        )
    }

    Scaffold(containerColor = background(appViewModel = appViewModel)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = "Back Navigation",
                        tint = white,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Edit Transaction",
                    fontSize = 22.sp,
                    fontFamily = latoFamily,
                    fontWeight = FontWeight.Bold,
                    color = white

                )
                Spacer(modifier = Modifier.weight(1f))
            }

            OutlineTextField(
                value = edtTitle.value,
                onValue = { text -> edtTitle.value = text },
                label = "Title",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Go,
                bgColor = white,
                enable = true,
                appViewModel = appViewModel,
                keyboardActions = KeyboardActions(onGo = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlineTextField(
                value = edtAmount.value,
                onValue = { number -> edtAmount.value = number },
                label = "Amount",
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                bgColor = white,
                enable = true,
                appViewModel = appViewModel,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlineTextField(
                value = selectedType.value,
                onValue = {},
                label = "Transaction Type",
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                bgColor = white,
                enable = false,
                appViewModel = appViewModel,
                keyboardActions = KeyboardActions()
            )
            Spacer(modifier = Modifier.height(10.dp))
            DatePickerDropdown(
                selectedDate = selectedDate.longValue,
                bgColor = white,
                appViewModel = appViewModel,
                onDateSelected = { date -> selectedDate.longValue = date },
                context = context
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    val editTitle = edtTitle.value.trim()
                    val editAmount = edtAmount.value.trim()
                    val editTimeStamp = selectedDate.longValue
                    val editCategory = selectedCategory.value.trim()
                    val date = dateFormater(editTimeStamp)
                    val amountRegex = Regex("^[0-9]+(\\.[0-9]+)?\$")
                    when {
                        editTitle.isBlank() && editAmount.isBlank() && type == "Select Transaction Type" -> {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                                .show()
                        }

                        editTitle.isBlank() -> {
                            Toast.makeText(context, "Please enter a title.", Toast.LENGTH_SHORT)
                                .show()
                        }

                        editAmount.isBlank() -> {
                            Toast.makeText(context, "Please enter an amount.", Toast.LENGTH_SHORT)
                                .show()
                        }

                        !amountRegex.matches(editAmount) -> {
                            Toast.makeText(
                                context,
                                "Invalid amount",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            val transaction =
                                Transaction(
                                    id,
                                    type,
                                    editTitle,
                                    editCategory,
                                    editAmount.toDouble(),
                                    editTimeStamp,
                                    date
                                )
                            appViewModel.editTransaction(transaction)
                            UpdateBalance.editUpdateBalance(
                                amount.toString(),
                                editAmount,
                                type,
                                appViewModel,
                                balance.value
                            )
                            navController.popBackStack()
                            Toast.makeText(
                                context, "Transaction Edit successfully.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = themeColor(appViewModel))
            ) {
                Text(
                    text = "Add Transaction",
                    fontSize = 14.sp,
                    fontFamily = latoFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Surface(color = background(appViewModel = appViewModel)) {
                when (selectedType.value) {
                    "Credit", "Debit" -> Column {

                        Text(
                            text = "Choose Category",
                            color = white,
                            fontSize = 16.sp,
                            fontFamily = latoFamily,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        )
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .padding(bottom = it.calculateBottomPadding()),
                            maxItemsInEachRow = 10
                        ) {

                            when (selectedType.value) {
                                "Debit" -> {
                                    debitCategories.forEach { (category, icon) ->
                                        CategoryChips(text = category,
                                            icon = icon,
                                            isSelected = selectedCategory.value == category,
                                            bgColor = white,
                                            textColor = black,
                                            appViewModel = appViewModel,
                                            onClick = {
                                                selectedCategory.value = category
                                            })
                                    }
                                }

                                "Credit" -> {
                                    creditCategories.forEach { (category, icon) ->
                                        CategoryChips(text = category,
                                            icon = icon,
                                            isSelected = selectedCategory.value == category,
                                            bgColor = white,
                                            textColor = black,
                                            appViewModel = appViewModel,
                                            onClick = {
                                                selectedCategory.value = category
                                            })
                                    }
                                }

                                else -> null
                            }
                        }


                    }

                    else -> null
                }

            }

        }
    }
}







