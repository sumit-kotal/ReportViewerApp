package com.assignment.reportviewerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PdfViewerState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val currentPdfUrl: String = "",
    val userInputUrl: String = ""
)

class PdfViewerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PdfViewerState())
    val uiState: StateFlow<PdfViewerState> = _uiState

    private var webViewInitialized = false

    init {
        loadDefaultPdf()
    }

    private fun loadDefaultPdf() {
        _uiState.update {
            it.copy(
                currentPdfUrl = "https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-2025/BalanceSheet.pdf",
                isLoading = true,
                errorMessage = null
            )
        }
    }

    fun loadPdf(url: String) {
        _uiState.update {
            it.copy(
                currentPdfUrl = url.ifEmpty {
                    "https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-2025/BalanceSheet.pdf"
                },
                isLoading = true,
                errorMessage = null,
                userInputUrl = url
            )
        }
    }

    fun updateUserInput(url: String) {
        _uiState.update { it.copy(userInputUrl = url) }
    }

    fun onLoadingComplete() {
        _uiState.update { it.copy(isLoading = false) }
    }

    fun onError(message: String) {
        _uiState.update { it.copy(errorMessage = message, isLoading = false) }
    }

    fun setWebViewInitialized() {
        webViewInitialized = true
    }

    fun isWebViewInitialized(): Boolean = webViewInitialized
}