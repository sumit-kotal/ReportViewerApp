package com.assignment.reportviewerapp.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ImageSelectionViewModel : ViewModel() {
    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> = _imageUri

    fun updateImageUri(uri: Uri?) {
        println("Updating URI to: $uri")
        _imageUri.value = uri
    }

    fun clearImage() {
        _imageUri.value = null
    }
}