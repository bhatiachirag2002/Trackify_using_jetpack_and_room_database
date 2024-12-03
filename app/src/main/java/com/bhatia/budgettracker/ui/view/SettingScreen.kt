package com.bhatia.budgettracker.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhatia.budgettracker.ui.theme.TealGreen
import com.bhatia.budgettracker.ui.theme.latoFamily
import com.bhatia.budgettracker.util.TransactionData
import com.bhatia.budgettracker.util.background
import com.bhatia.budgettracker.util.blackColor
import com.bhatia.budgettracker.util.whiteColor
import com.bhatia.budgettracker.viewmodel.AppViewModel

@Composable
fun SettingScreen(appViewModel: AppViewModel) {
    val themeChecked = appViewModel.themeChecked.collectAsState(initial = false)
    val selectedColor = appViewModel.colorSelected.collectAsState(initial = "Teal Green")

    val white = whiteColor(appViewModel)
    val black = blackColor(appViewModel)

    Scaffold(containerColor = background(appViewModel)) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding() + 10.dp)
        ) {
            Text(
                text = "Settings",
                fontSize = 45.sp,
                fontFamily = latoFamily,
                fontWeight = FontWeight.Black,
                color = white,
                letterSpacing = 0.3.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            ColorCard(modifier = Modifier.padding(top = 8.dp),
                bgColor = black,
                textColor = white,
                isSelected = selectedColor.value,
                onTypeSelected = { color ->
                    appViewModel.updateThemeColor(color)
                })
            SwitchCard(modifier = Modifier.padding(top = 10.dp),
                text = "Theme Preference",
                checked = themeChecked.value,
                bgColor = black,
                textColor = white,
                icon = Icons.Default.WbSunny,
                onChecked = { check ->
                    appViewModel.updateThemePreference(check)
                })
        }
    }
}


@Composable
fun SwitchCard(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    checked: Boolean,
    bgColor: Color,
    textColor: Color,
    onChecked: (Boolean) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(20),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(bgColor)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = latoFamily,
                color = textColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                modifier = Modifier.height(60.dp),
                checked = checked,
                onCheckedChange = onChecked,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = TealGreen,
                    checkedBorderColor = textColor,
                    uncheckedThumbColor = Color.White,
                    checkedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Red,
                    uncheckedBorderColor = textColor
                )
            )
        }
    }
}

@Composable
fun ColorCard(
    modifier: Modifier = Modifier,
    bgColor: Color,
    textColor: Color,
    isSelected: String,
    onTypeSelected: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(20),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(bgColor)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ColorLens,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Theme Color",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = latoFamily,
                color = textColor,
            )
            Spacer(modifier = Modifier.weight(1f))

            LazyRow(verticalAlignment = Alignment.CenterVertically) {
                val colors = TransactionData.colors

                items(colors.size) { index ->
                    ColorChip(isSelected = colors[index].name == isSelected, onClick = {
                        onTypeSelected(colors[index].name)
                    }, color = colors[index].color)
                }
            }
        }
    }
}


@Composable
fun ColorChip(
    isSelected: Boolean, onClick: () -> Unit, color: Color
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 7.dp)
            .size(if (isSelected) 40.dp else 25.dp)
            .clickable(onClick = onClick),
        color = color,
        shape = CircleShape,
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            )
        }
    }
}
