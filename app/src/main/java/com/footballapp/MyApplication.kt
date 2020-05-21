package com.footballapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(
                listOf(
                    repositoryModule,
                    apiModule,
                    networkModule,
                    viewModelModule,
                    firebaseAuthModule,
                    connectionModule
                )
            )
        }
    }
}