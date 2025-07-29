package xyz.fartem.hashcheckerx.screens.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.fartem.hashcheckerx.BuildConfig
import xyz.fartem.hashcheckerx.settings.api.SettingsWrapper
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsRepository
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsWrapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsWrapperModule {
    @Provides
    @Singleton
    fun bingSettingsWrapper(@ApplicationContext context: Context): SettingsWrapper {
        return SharedPreferencesSettingsWrapper(
            sharedPreferencesSettingsRepository = SharedPreferencesSettingsRepository(
                context.getSharedPreferences(
                    BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE
                ),
            ),
            stringResourcesProvider = {
                context.getString(it)
            },
        )
    }
}
