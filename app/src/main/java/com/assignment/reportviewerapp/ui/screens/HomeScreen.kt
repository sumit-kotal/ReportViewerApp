package com.assignment.reportviewerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            // Custom Bottom Navigation using buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // PDF Viewer Button
                    IconButton(
                        onClick = { navController.navigate("pdf_viewer_screen") }
                    ) {
                        Icon(Icons.Filled.PictureAsPdf, contentDescription = "PDF Viewer")
                    }
                    // Image Selection Button
                    IconButton(
                        onClick = { navController.navigate("image_selection_screen") }
                    ) {
                        Icon(Icons.Filled.PhotoCamera, contentDescription = "Image Selection")
                    }
                    // List Button
                    IconButton(
                        onClick = { navController.navigate("list_screen") }
                    ) {
                        Icon(Icons.Filled.List, contentDescription = "List")
                    }
                }
            }
        }
    ) { innerPadding ->
        // Main content goes here
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Welcome to the Home Screen!", fontSize = 24.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}