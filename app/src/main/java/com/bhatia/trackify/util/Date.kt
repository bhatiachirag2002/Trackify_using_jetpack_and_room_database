package com.bhatia.trackify.util

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bhatia.trackify.viewmodel.AppViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun DatePickerDropdown(
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
    context: Context,
    bgColor: Color,
    appViewModel: AppViewModel
) {
    val isDialogOpen = remember { mutableStateOf(false) }
    val currentDate = Calendar.getInstance()


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        OutlinedTextField(
            value = dateFormater(selectedDate),
            onValueChange = {},
            label = { Text("Select Date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isDialogOpen.value = true },
            readOnly = true,
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Date Picker Icon",
                    tint = bgColor
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = themeColor(appViewModel = appViewModel),
                disabledLabelColor = bgColor,
                disabledTextColor = bgColor
            )
        )
    }

    if (isDialogOpen.value) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)
                onDateSelected(selectedCalendar.timeInMillis) // Pass date as Long
                isDialogOpen.value = false // Close dialog
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).apply {
            // Set date range (last 1 year 11 months to current date)
            val minDate = Calendar.getInstance().apply {
                add(Calendar.YEAR, -3)
                add(Calendar.MONTH, -6)
            }.timeInMillis

            datePicker.minDate = minDate
            datePicker.maxDate = currentDate.timeInMillis
        }.show()
    }
}


fun dateFormater(number: Long): String {
    return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(number)
}

fun monthFormater(number: Long): String {
    return SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(number)
}

fun getStartOfYear(year: Int): Long {
    return Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, Calendar.APRIL)
        set(Calendar.DAY_OF_MONTH, 1)
    }.timeInMillis
}

fun getEndOfYear(year: Int): Long {
    return Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, Calendar.MARCH)
        set(Calendar.DAY_OF_MONTH, 31)
    }.timeInMillis
}