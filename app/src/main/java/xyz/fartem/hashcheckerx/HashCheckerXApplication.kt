package xyz.fartem.hashcheckerx

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import xyz.fartem.hashcheckerx.hash_generator.startup.HashGeneratorStartup
import java.security.Security

@HiltAndroidApp
class HashCheckerXApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        HashGeneratorStartup.start()
    }
}
