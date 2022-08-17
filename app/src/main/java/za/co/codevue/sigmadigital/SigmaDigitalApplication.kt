package za.co.codevue.sigmadigital

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SigmaDigitalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // init timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}