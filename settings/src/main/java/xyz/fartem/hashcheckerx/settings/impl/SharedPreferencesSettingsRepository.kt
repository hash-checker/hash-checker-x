package xyz.fartem.hashcheckerx.settings.impl

import android.content.SharedPreferences
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.model.SettingsEntry
import androidx.core.content.edit

class SharedPreferencesSettingsRepository(
    private val sharedPreferences: SharedPreferences,
) : SettingsRepository() {
    override fun getBooleanValue(settingsEntry: SettingsEntry): Boolean {
        return when (settingsEntry) {
            SettingsEntry.UPPER_CASE,
            SettingsEntry.VIBRATION -> {
                sharedPreferences.getBoolean(
                    settingsEntry.key,
                    settingsEntry.defaultValue as Boolean,
                )
            }

            else -> false
        }
    }

    override fun setBooleanValue(settingsEntry: SettingsEntry, value: Boolean) {
        when (settingsEntry) {
            SettingsEntry.UPPER_CASE,
            SettingsEntry.VIBRATION -> {
                sharedPreferences.edit { putBoolean(settingsEntry.key, value) }
            }

            else -> {}
        }
    }
}
