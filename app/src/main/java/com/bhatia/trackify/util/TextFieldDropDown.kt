package com.bhatia.budgettracker.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.bhatia.budgettracker.ui.theme.latoFamily
import com.bhatia.budgettracker.viewmodel.AppViewModel

@Composable
fun TextFieldDropdown(
    modifier: Modifier = Modifier,
    selected: String,
    onTypeSelected: (String) -> Unit,
    list: List<String>,
    bgColor: Color,
    appViewModel: AppViewModel,
    label: String

) {
    val expanded = remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            label = { Text(label) },
            modifier = modifier
                .clickable {
                    expanded.value = !expanded.value
                },
            readOnly = true, // Makes it non-editable, dropdown only
            enabled = false,
            trailingIcon = {

                Icon(
                    imageVector = if (expanded.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    tint = bgColor
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = themeColor(appViewModel),
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                disabledLabelColor = bgColor,
                disabledTextColor = bgColor
            )
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth(),
            containerColor = background(appViewModel),
        ) {
            list.forEach { type ->
                DropdownMenuItem(
                    onClick = {
                        onTypeSelected(type) // Callback to update the selected type
                        expanded.value = false // Close dropdown
                    },
                    text = {
                        Text(
                            text = type,
                            fontFamily = latoFamily,
                            fontSize = 14.sp,
                            color = whiteColor(
                                appViewModel
                            )
                        )
                    }
                )
            }
        }
    }
}