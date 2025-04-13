package com.assignment.reportviewerapp.di

import com.assignment.reportviewerapp.viewmodel.ImageSelectionViewModel
import com.assignment.reportviewerapp.viewmodel.PdfViewerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        ImageSelectionViewModel()
        PdfViewerViewModel()
    }
}