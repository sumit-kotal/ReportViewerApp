package com.assignment.reportviewerapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assignment.reportviewerapp.R
import com.assignment.reportviewerapp.ui.theme.ReportViewerAppTheme
import com.assignment.reportviewerapp.utils.AppPreferences
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val appPreferences: AppPreferences = koinInject()

    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Splash Screen",
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )

    LaunchedEffect(Unit) {
        delay(2000) // Show splash for 2 seconds
        if (appPreferences.isLoggedIn) {
            navController.navigate("home_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        } else {
            navController.navigate("login_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }
}
