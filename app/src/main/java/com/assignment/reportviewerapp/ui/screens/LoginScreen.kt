package com.assignment.reportviewerapp.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.assignment.reportviewerapp.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import android.content.Context
import android.content.SharedPreferences
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    var isSigningIn by remember { mutableStateOf(false) }

    // Google Sign-In configuration
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(
        context, GoogleSignInOptions.DEFAULT_SIGN_IN
    )

    val signInResultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                // Save user info to SharedPreferences
                sharedPreferences.edit().apply {
                    putString("user_id", it.id)
                    putString("user_name", it.displayName)
                    putString("user_email", it.email)
                    putString("user_photo_url", it.photoUrl.toString())
                    apply()
                }
                // Navigate to the next screen after successful login
                navController.navigate("home_screen")
            }
        } catch (e: ApiException) {
            // Handle sign-in error
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to the App", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isSigningIn = true
                val signInIntent = googleSignInClient.signInIntent
                signInResultLauncher.launch(signInIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In with Google")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isSigningIn) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}