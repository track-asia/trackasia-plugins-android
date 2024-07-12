package org.trackasia.android.plugins.testapp

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import org.trackasia.android.TrackAsia
import timber.log.Timber

class PluginApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
        initializeLogger()
        TrackAsia.getInstance(this)
    }

    private fun initializeLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
