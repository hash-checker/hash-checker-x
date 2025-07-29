package xyz.fartem.hashcheckerx.screens.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.fartem.hashcheckerx.BuildConfig
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Provides
    @Singleton
    fun bingSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SharedPreferencesSettingsRepository(
            context.getSharedPreferences(
                BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE
            ),
        )
    }
}
