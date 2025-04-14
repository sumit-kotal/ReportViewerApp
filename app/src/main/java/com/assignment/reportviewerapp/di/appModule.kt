package com.assignment.reportviewerapp.di

import android.content.Context
import android.content.SharedPreferences
import com.assignment.reportviewerapp.repository.ListItemRepository
import com.assignment.reportviewerapp.repository.db.AppDatabase
import com.assignment.reportviewerapp.utils.AppPreferences
import com.assignment.reportviewerapp.viewmodel.ImageSelectionViewModel
import com.assignment.reportviewerapp.viewmodel.ListViewModel
import com.assignment.reportviewerapp.viewmodel.LoginViewModel
import com.assignment.reportviewerapp.viewmodel.PdfViewerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

// di/AppModule.kt
val appModule = module {
    // Database
    single { AppDatabase.getDatabase(get()).listItemDao() }

    // SharedPreferences
    single { provideSharedPreferences(androidContext()) }
    single { AppPreferences(get()) }

    // Repositories
    single { ListItemRepository(get()) }

    // ViewModels
    viewModel { ListViewModel(get()) }
    viewModel { ImageSelectionViewModel() }
    viewModel { PdfViewerViewModel() }
    viewModel { LoginViewModel(get()) }
}

private fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
}