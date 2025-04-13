package com.assignment.reportviewerapp.di

import com.assignment.reportviewerapp.viewmodel.ImageSelectionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::ImageSelectionViewModel)
}