package com.assignment.reportviewerapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assignment.reportviewerapp.ui.screens.HomeScreen
import com.assignment.reportviewerapp.ui.ImageSelectionActivity
import com.assignment.reportviewerapp.ui.screens.ListScreen
import com.assignment.reportviewerapp.ui.screens.LoginScreen
import com.assignment.reportviewerapp.ui.screens.PdfViewerScreen
import com.assignment.reportviewerapp.ui.screens.SplashScreen
import com.assignment.reportviewerapp.ui.theme.ReportViewerAppTheme
import com.assignment.reportviewerapp.viewmodel.ImageSelectionViewModel
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.androidx.compose.koinViewModel
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReportViewerAppTheme {
                val navController = rememberNavController()

                val onNavigateToImageSelection: () -> Unit = {
                    val intent = Intent(this, ImageSelectionActivity::class.java)
                    startActivity(intent)
                }

                MainNavHost(
                    navController = navController,
                    onNavigateToImageSelection = onNavigateToImageSelection
                )
            }
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                println("FCM registration token failed")
                return@addOnCompleteListener
            }

            // Get the new FCM token
            val token = task.result
            println("FCM Registration Token: $token")

        }


        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            // Get the refreshed FCM token
            val token = task.result
            println("Refreshed token: $token")
        }
    }
}



@Composable
fun MainNavHost(
    onNavigateToImageSelection: () -> Unit,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        composable("login_screen") {
            LoginScreen(navController)
        }
        composable("home_screen") {
            HomeScreen(navController,onNavigateToImageSelection)
        }
        composable("pdf_viewer_screen") {
            PdfViewerScreen(navController = navController)
        }
        composable("image_selection_screen") {
        }
        composable("list_screen") {
            ListScreen(navController)
        }
    }
}