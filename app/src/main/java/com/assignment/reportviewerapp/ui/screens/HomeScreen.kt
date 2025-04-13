package com.assignment.reportviewerapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,onNavigateToImageSelection: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome to Report Viewer App") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This app allows you to view PDF reports, select images from your gallery or camera, and explore a list of items.",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 24.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            // PDF Viewer
            Button(
                onClick = {
                    navController.navigate("pdf_viewer_screen")
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Icon(Icons.Filled.PictureAsPdf, contentDescription = "PDF Viewer", modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("PDF Viewer")
            }

            // Image Selection
            Button(
                onClick = { onNavigateToImageSelection() },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Icon(Icons.Filled.PhotoCamera, contentDescription = "Image Selection", modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Image Selection")
            }

            // List
            Button(
                onClick = { navController.navigate("list_screen") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = "List", modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("List")
            }


            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("login_screen") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout")
            }
        }
    }
}
