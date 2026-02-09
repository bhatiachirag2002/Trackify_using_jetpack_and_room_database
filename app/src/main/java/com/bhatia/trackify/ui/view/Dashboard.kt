package com.bhatia.trackify.ui.view


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DonutSmall
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bhatia.trackify.util.themeColor
import com.bhatia.trackify.viewmodel.AppViewModel

@Composable
fun Dashboard(mainNavController: NavController, appViewModel: AppViewModel) {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold {
        Surface(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = "Home",
            ) {
                composable("Home") {
                    HomeScreen(navController = mainNavController, appViewModel = appViewModel)
                }
                composable("Statics") {
                    StaticsScreen(appViewModel = appViewModel)
                }
                composable("Transaction") {
                    TransactionScreen(
                        navController = mainNavController,
                        appViewModel = appViewModel
                    )
                }
                composable("Setting") {
                    SettingScreen(appViewModel = appViewModel)
                }
            }
            Box {
                Surface(
                    shadowElevation = 12.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 15.dp)
                        .padding(bottom = 20.dp)
                        .align(Alignment.BottomCenter),
                    color = themeColor(appViewModel),
                    shape = RoundedCornerShape(30)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        NavItem(
                            icon = Icons.Rounded.Home,
                            label = "Home",
                            selected = currentDestination == "Home",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigate("Home") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        )

                        NavItem(
                            icon = Icons.Filled.DonutSmall,
                            label = "Statics",
                            selected = currentDestination == "Statics",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigate("Statics") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        )

                        NavItem(
                            icon = Icons.Default.Receipt,
                            label = "Transactions",
                            selected = currentDestination == "Transaction",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigate("Transaction") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        )

                        NavItem(
                            icon = Icons.Default.Settings,
                            label = "Setting",
                            selected = currentDestination == "Setting",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigate("Setting") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = if (selected) ({}) else onClick,
        modifier = modifier.height(33.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            modifier = Modifier.size(28.dp),
            tint = if (selected) Color.White else Color.Black
        )
    }


}