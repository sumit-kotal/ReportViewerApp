package com.assignment.reportviewerapp.ui.screens

import androidx.compose.foundation.layout.*
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
fun ImageSelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Image Selection", fontSize = 24.sp)

        // Image picking functionality would go here
        Button(onClick = { /* Open Camera or Gallery */ }) {
            Text("Select Image")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageSelectionScreenPreview() {
    ImageSelectionScreen(navController = rememberNavController())
}