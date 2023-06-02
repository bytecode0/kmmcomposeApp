package com.kashif.android

import android.app.Application
import com.kashif.common.di.initKoin
import com.kashif.common.platformModule
import org.koin.android.ext.koin.androidContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@App)
            modules(platformModule())
        }
    }
}
