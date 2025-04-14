package com.assignment.reportviewerapp.ui.screens

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.assignment.reportviewerapp.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import androidx.activity.compose.rememberLauncherForActivityResult
import com.assignment.reportviewerapp.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = koinViewModel()
    val loginState by viewModel.loginState.collectAsState()

    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .build()
        )
    }

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            viewModel.handleSignInResult(task)
        } catch (e: Exception) {
            viewModel.handleSignInResult(null)
        }
    }

    // Handle login state changes
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginViewModel.LoginState.Success -> {
                navController.navigate("home_screen") {
                    popUpTo("login_screen") { inclusive = true }
                }
            }
            else -> {}
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

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                signInLauncher.launch(googleSignInClient.signInIntent)
            },
            modifier = Modifier.fillMaxWidth(0.8f),
            enabled = loginState !is LoginViewModel.LoginState.Loading
        ) {
            Text("Sign In with Google")
        }


        Button(
            onClick = {
                viewModel.bypassedLogin()
            },
            modifier = Modifier.fillMaxWidth(0.8f),
            enabled = loginState !is LoginViewModel.LoginState.Loading
        ) {
            Text("By pass Google Sign in")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (loginState) {
            is LoginViewModel.LoginState.Loading -> CircularProgressIndicator()
            is LoginViewModel.LoginState.Error -> {
                val error = (loginState as LoginViewModel.LoginState.Error).message
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (error.contains("network", ignoreCase = true)) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                signInLauncher.launch(googleSignInClient.signInIntent)
                            }
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }
            else -> {}
        }
    }
}