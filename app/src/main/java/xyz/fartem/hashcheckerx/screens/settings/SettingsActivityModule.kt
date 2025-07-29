package xyz.fartem.hashcheckerx.screens.settings

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.fartem.hashcheckerx.BuildConfig
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsRepository

@Module
@InstallIn(ActivityComponent::class)
object SettingsActivityModule {
    @Provides
    fun bindSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SharedPreferencesSettingsRepository(
            context.getSharedPreferences(
                BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE
            ),
        )
    }
}
