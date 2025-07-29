package xyz.fartem.hashcheckerx.screens.main

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.fartem.hashcheckerx.R
import xyz.fartem.hashcheckerx.core.clipboard.api.Clipboard
import xyz.fartem.hashcheckerx.core.clipboard.impl.SystemClipboard
import xyz.fartem.hashcheckerx.core.logger.api.Logger
import xyz.fartem.hashcheckerx.core.logger.impl.OrhanObutLogger
import xyz.fartem.hashcheckerx.core.vibrator.api.Vibrator
import xyz.fartem.hashcheckerx.core.vibrator.impl.SystemVibrator
import xyz.fartem.hashcheckerx.hash_generator.api.HashComparator
import xyz.fartem.hashcheckerx.hash_generator.api.HashGenerator
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashComparator
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {
    @Provides
    fun bindHashGenerator(): HashGenerator {
        return JdkHashGenerator(HashType.MD5)
    }

    @Provides
    fun bindHashComparator(): HashComparator {
        return JdkHashComparator()
    }

    @Provides
    fun bindLogger(): Logger {
        return OrhanObutLogger()
    }

    @Provides
    fun bindVibrator(@ApplicationContext context: Context): Vibrator {
        return SystemVibrator(context)
    }

    @Provides
    fun bindClipboard(@ApplicationContext context: Context): Clipboard {
        return SystemClipboard(context, context.getString(R.string.app_name))
    }
}
