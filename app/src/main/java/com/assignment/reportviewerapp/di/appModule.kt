package com.assignment.reportviewerapp.di

import com.assignment.reportviewerapp.repository.ListItemRepository
import com.assignment.reportviewerapp.repository.db.AppDatabase
import com.assignment.reportviewerapp.viewmodel.ImageSelectionViewModel
import com.assignment.reportviewerapp.viewmodel.ListViewModel
import com.assignment.reportviewerapp.viewmodel.PdfViewerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        ImageSelectionViewModel()
        PdfViewerViewModel()
        ListViewModel(get())
    }
    single { ListItemRepository(get()) }
    single { AppDatabase.getDatabase(get()).listItemDao() }
}