package com.trackasia.android.plugins.testapp

import android.app.Application
import com.trackasia.android.TrackAsia
import timber.log.Timber

class PluginApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeLogger()
        TrackAsia.getInstance(this)
    }

    private fun initializeLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
