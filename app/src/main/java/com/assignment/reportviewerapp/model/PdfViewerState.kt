package com.assignment.reportviewerapp.model

data class PdfViewerState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val currentPdfUrl: String = "",
    val userInputUrl: String = ""
)

