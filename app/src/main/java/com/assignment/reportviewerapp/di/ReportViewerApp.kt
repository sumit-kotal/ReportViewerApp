package com.assignment.reportviewerapp.di

import android.app.Application
import com.assignment.reportviewerapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReportViewerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ReportViewerApp)
            modules(appModule)
        }
    }
}