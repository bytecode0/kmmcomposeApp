package com.dogify.android

import android.app.Application
import com.dogify.common.di.initKoin
import com.dogify.common.platformModule
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
