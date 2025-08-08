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
import xyz.fartem.hashcheckerx.hash_generator.impl.DefaultHashComparator
import xyz.fartem.hashcheckerx.hash_generator.impl.DefaultHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.impl.providers.crc32.Crc32HashProvider
import xyz.fartem.hashcheckerx.hash_generator.impl.providers.jdk.JdkHashProvider
import xyz.fartem.hashcheckerx.hash_generator.impl.providers.keccakj.KeccakjHashProvider

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {
    @Provides
    fun provideHashGenerator(): HashGenerator {
        return DefaultHashGenerator(
            listOf(
                JdkHashProvider(),
                KeccakjHashProvider(),
                Crc32HashProvider(),
            ),
        )
    }

    @Provides
    fun provideHashComparator(): HashComparator {
        return DefaultHashComparator()
    }

    @Provides
    fun provideLogger(): Logger {
        return OrhanObutLogger()
    }

    @Provides
    fun provideVibrator(@ApplicationContext context: Context): Vibrator {
        return SystemVibrator(context)
    }

    @Provides
    fun provideClipboard(@ApplicationContext context: Context): Clipboard {
        return SystemClipboard(context, context.getString(R.string.app_name))
    }
}
