package com.bhatia.budgettracker.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bhatia.budgettracker.viewmodel.AppViewModel

@Composable
fun OutlineTextField(
    value: String,
    onValue: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    bgColor: Color,
    enable: Boolean,
    appViewModel: AppViewModel
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValue,
        enabled = enable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        label = {
            Text(
                text = label,
                color = bgColor
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),

        keyboardActions = keyboardActions,
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = bgColor,
            unfocusedBorderColor = themeColor(appViewModel),
            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red,
            cursorColor = bgColor,
            focusedLabelColor = bgColor,
            focusedTextColor = bgColor,
            errorTextColor = bgColor,
        )
    )
}