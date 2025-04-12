package com.assignment.reportviewerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.reportviewerapp.ui.screens.HomeScreen
import com.assignment.reportviewerapp.ui.screens.ImageSelectionScreen
import com.assignment.reportviewerapp.ui.screens.ListScreen
import com.assignment.reportviewerapp.ui.screens.LoginScreen
import com.assignment.reportviewerapp.ui.screens.PdfViewerScreen
import com.assignment.reportviewerapp.ui.screens.SplashScreen
import com.assignment.reportviewerapp.ui.theme.ReportViewerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReportViewerAppTheme {
                MainNavHost()
            }
        }
    }
}

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

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
            HomeScreen(navController)
        }
        composable("pdf_viewer_screen") {
            PdfViewerScreen(navController)
        }
        composable("image_selection_screen") {
            ImageSelectionScreen(navController)
        }
        composable("list_screen") {
            ListScreen(navController)
        }
    }
}