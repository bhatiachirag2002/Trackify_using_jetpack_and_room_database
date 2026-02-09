package com.bhatia.trackify.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.bhatia.trackify.ui.theme.Blue
import com.bhatia.trackify.ui.theme.BlueLight
import com.bhatia.trackify.ui.theme.RaspberryRed
import com.bhatia.trackify.ui.theme.RaspberryRedLight
import com.bhatia.trackify.ui.theme.TealGreen
import com.bhatia.trackify.ui.theme.TealGreenLight
import com.bhatia.trackify.viewmodel.AppViewModel

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