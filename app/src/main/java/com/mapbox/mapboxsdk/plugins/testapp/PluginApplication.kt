package com.trackasia.android.plugins.testapp

import android.app.Application
import com.trackasia.android.Trackasia
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class PluginApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
        initializeLogger()
        Trackasia.getInstance(this)
    }

    private fun initializeLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
