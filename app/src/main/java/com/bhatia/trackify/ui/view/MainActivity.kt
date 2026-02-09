package com.bhatia.budgettracker.ui.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bhatia.budgettracker.R
import com.bhatia.budgettracker.db.AppDB
import com.bhatia.budgettracker.repository.AppRepo
import com.bhatia.budgettracker.ui.theme.BudgetTrackerTheme
import com.bhatia.budgettracker.viewmodel.AppViewModel
import com.bhatia.budgettracker.viewmodel.AppViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_BudgetTracker)
        super.onCreate(savedInstanceState)
        setupVM()
        setContent {
            val themeChecked = appViewModel.themeChecked.collectAsState(initial = false)
            val systemBarStyle = if (themeChecked.value) {
                SystemBarStyle.dark(
                    Color.Transparent.toArgb(),
                )
            } else {
                SystemBarStyle.light(
                    Color.Transparent.toArgb(),
                    Color.Transparent.toArgb(),
                )
            }
            enableEdgeToEdge(statusBarStyle = systemBarStyle, navigationBarStyle = systemBarStyle)
            BudgetTrackerTheme(darkTheme = themeChecked.value) {
                NavComponent(appViewModel)
            }
        }
    }

    private fun setupVM() {
        val sharedPreferences = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val appRepo = AppRepo(AppDB(this), sharedPreferences)
        val viewModelProviderFactory = AppViewModelFactory(appRepo)
        appViewModel = ViewModelProvider(this, viewModelProviderFactory)[AppViewModel::class.java]
    }

}


@Composable
fun NavComponent(appViewModel: AppViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "SplashScreen") {
        composable("SplashScreen",
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))
            }
        ) {
            SplashScreen(navController = navController, appViewModel = appViewModel)
        }
        composable("Dashboard") {
            Dashboard(mainNavController = navController, appViewModel = appViewModel)
        }
        composable("Add",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, tween(1000)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(1000)
                )
            }
        ) {
            AddTransactionScreen(navController = navController, appViewModel = appViewModel)
        }

        composable(
            "Edit?id={id}&title={title}&amount={amount}&type={type}&timeStamp={timeStamp}&category={category}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType },
                navArgument("amount") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("timeStamp") { type = NavType.LongType },
                navArgument("category") { type = NavType.StringType }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(500)
                )
            }
        ) { backStackEntry ->
            val amountString = backStackEntry.arguments?.getString("amount") ?: "0.0"
            val amount = amountString.toDouble()
            EditTransactionScreen(
                navController = navController,
                appViewModel = appViewModel,
                title = backStackEntry.arguments!!.getString("title") ?: "",
                amount = amount,
                category = backStackEntry.arguments!!.getString("category") ?: "",
                type = backStackEntry.arguments!!.getString("type") ?: "",
                timeStamp = backStackEntry.arguments!!.getLong("timeStamp"),
                id = backStackEntry.arguments!!.getInt("id")

            )
        }

    }
}
