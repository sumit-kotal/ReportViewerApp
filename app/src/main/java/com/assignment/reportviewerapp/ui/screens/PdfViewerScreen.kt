package com.assignment.reportviewerapp.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.assignment.reportviewerapp.viewmodel.PdfViewerViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PdfViewerScreen(
    navController: NavController,
    viewModel: PdfViewerViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    // Default URL encoding
    val pdfUrl = remember(uiState.currentPdfUrl) {
        "https://docs.google.com/gview?embedded=true&url=${Uri.encode(uiState.currentPdfUrl)}"
    }

    LaunchedEffect(Unit) {
        if (!viewModel.isWebViewInitialized()) {
            viewModel.setWebViewInitialized()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // URL Input Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = uiState.userInputUrl,
                onValueChange = { viewModel.updateUserInput(it) },
                label = { Text("Enter new PDF URL") },
                modifier = Modifier.weight(1f),
                placeholder = { Text(uiState.currentPdfUrl) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { viewModel.loadPdf(uiState.userInputUrl) }
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Load PDF")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (uiState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading PDF...")
                }
            }

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                viewModel.onLoadingComplete()
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                errorCode: Int,
                                description: String?,
                                failingUrl: String?
                            ) {
                                viewModel.onError(description ?: "Unknown error occurred")
                            }
                        }
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            loadWithOverviewMode = true
                            useWideViewPort = true
                            setSupportZoom(true)
                            builtInZoomControls = true
                            displayZoomControls = false
                        }
                        loadUrl(pdfUrl)
                    }.also { webViewRef = it }
                },
                update = { webView ->
                    if (!uiState.isLoading) {
                        webView.loadUrl(pdfUrl)
                    }
                }
            )

            if (uiState.errorMessage != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Error loading PDF",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(uiState.errorMessage ?: "Unknown error")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadPdf(uiState.currentPdfUrl) }
                    ) {
                        Text("Retry")
                    }
                }
            }
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Close Viewer")
        }
    }
}