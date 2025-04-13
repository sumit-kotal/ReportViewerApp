package com.assignment.reportviewerapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.assignment.reportviewerapp.ui.theme.ReportViewerAppTheme
import com.assignment.reportviewerapp.viewmodel.ImageSelectionViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReportViewerAppTheme {
                val context = LocalContext.current
                val viewModel: ImageSelectionViewModel = koinViewModel()

                // State to store camera URI
                var cameraUri by remember { mutableStateOf<Uri?>(null) }

                // Camera launcher
                val cameraLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.TakePicture()
                ) { success ->
                    if (success && cameraUri != null) {
                        viewModel.updateImageUri(cameraUri)
                    }
                    cameraUri = null
                }

                // Gallery launcher
                val galleryLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.GetContent()
                ) { uri ->
                    uri?.let {
                        // Take persistable permission for gallery images
                        context.contentResolver.takePersistableUriPermission(
                            it,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        viewModel.updateImageUri(it)
                    }
                }

                // Permission launcher
                val cameraPermissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    if (isGranted && cameraUri != null) {
                        cameraLauncher.launch(cameraUri!!)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier
                            .weight(0.6f)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        val imageUri by viewModel.imageUri
                        imageUri?.let { uri ->

                            val isUriValid by remember(uri) {
                                derivedStateOf {
                                    runCatching {
                                        context.contentResolver.openInputStream(uri)?.use { true } ?: false
                                    }.getOrDefault(false)
                                }
                            }

                            if (isUriValid) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(context)
                                            .data(uri)
                                            .diskCacheKey(uri.toString())
                                            .memoryCacheKey(uri.toString())
                                            .build()
                                    ),
                                    contentDescription = "Selected image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentScale = ContentScale.Fit
                                )
                            } else {
                                Text("Couldn't load image (URI invalid)")
                            }
                        } ?: Text("No image selected")
                    }

                    // Buttons at bottom (40% of screen)
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Select from Gallery")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                val timeStamp = SimpleDateFormat(
                                    "yyyyMMdd_HHmmss",
                                    Locale.getDefault()
                                ).format(Date())
                                val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                                val imageFile = File.createTempFile(
                                    "JPEG_${timeStamp}_",
                                    ".jpg",
                                    storageDir
                                ).apply { createNewFile() }

                                cameraUri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.provider",
                                    imageFile
                                )

                                // Check if URI is valid before launching camera
                                if (cameraUri != null) {
                                    if (ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.CAMERA
                                        ) == PackageManager.PERMISSION_GRANTED
                                    ) {
                                        cameraLauncher.launch(cameraUri!!)
                                    } else {
                                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Take Photo")
                        }
                    }
                }
            }
        }
    }
}