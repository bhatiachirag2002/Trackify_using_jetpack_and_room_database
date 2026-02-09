package com.bhatia.trackify.ui.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bhatia.trackify.R
import com.bhatia.trackify.ui.theme.pacificoFamily
import com.bhatia.trackify.util.themeColor
import com.bhatia.trackify.viewmodel.AppViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, appViewModel: AppViewModel) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("Dashboard") {
            popUpTo("SplashScreen") { inclusive = true }
        }
    }

    Scaffold {
        Surface(
            Modifier
                .fillMaxSize(), color = themeColor(appViewModel)
        ) {

            Box(contentAlignment = Alignment.Center) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.purse),
                        contentDescription = null,
                        Modifier.width(160.dp),
                        contentScale = ContentScale.FillWidth
                    )
                    Text(
                        text = "Trackify",
                        fontFamily = pacificoFamily,
                        fontSize = 30.sp,
                        color = Color.White
                    )

                }
                Text(
                    "Developed by Chirag Bhatia",
                    fontFamily = pacificoFamily,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = it.calculateBottomPadding() + 10.dp)
                )
            }

        }
    }


}


