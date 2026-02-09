package com.bhatia.budgettracker.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.bhatia.budgettracker.ui.theme.Blue
import com.bhatia.budgettracker.ui.theme.BlueLight
import com.bhatia.budgettracker.ui.theme.RaspberryRed
import com.bhatia.budgettracker.ui.theme.RaspberryRedLight
import com.bhatia.budgettracker.ui.theme.TealGreen
import com.bhatia.budgettracker.ui.theme.TealGreenLight
import com.bhatia.budgettracker.viewmodel.AppViewModel

@Composable
fun background(appViewModel: AppViewModel): Color {
    val themeChecked = appViewModel.themeChecked.collectAsState(initial = false)
    return if (themeChecked.value) Color(0xFF242222) else Color(0xFFFFFBFE)
}

@Composable
fun whiteColor(appViewModel: AppViewModel): Color {
    val themeChecked = appViewModel.themeChecked.collectAsState(initial = false)
    return if (themeChecked.value) Color.White else Color.Black
}

@Composable
fun blackColor(appViewModel: AppViewModel): Color {
    val themeChecked = appViewModel.themeChecked.collectAsState(initial = false)
    return if (themeChecked.value) Color.Black else Color.White
}

@Composable
fun themeColor(appViewModel: AppViewModel): Color {
    val colorSelected = appViewModel.colorSelected.collectAsState(initial = false)
    return when (colorSelected.value) {
        "Raspberry Red" -> RaspberryRed
        "Blue" -> Blue
        else -> TealGreen
    }
}


@Composable
fun themeLightColor(appViewModel: AppViewModel): Color {
    val colorSelected = appViewModel.colorSelected.collectAsState(initial = false)
    return when (colorSelected.value) {
        "Raspberry Red" -> RaspberryRedLight
        "Blue" -> BlueLight
        else -> TealGreenLight
    }
}