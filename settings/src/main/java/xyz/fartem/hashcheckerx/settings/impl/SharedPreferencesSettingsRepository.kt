package xyz.fartem.hashcheckerx.settings.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.model.SettingsEntry

class SharedPreferencesSettingsRepository(
    private val sharedPreferences: SharedPreferences,
) : SettingsRepository() {
    override fun getBooleanValue(settingsEntry: SettingsEntry): Boolean {
        return when (settingsEntry) {
            SettingsEntry.UPPER_CASE,
            SettingsEntry.VIBRATION,
            SettingsEntry.EXPAND_HASH_FIELDS -> {
                sharedPreferences.getBoolean(
                    settingsEntry.key,
                    settingsEntry.defaultValue as Boolean,
                )
            }

            SettingsEntry.FEEDBACK,
            SettingsEntry.PRIVACY_POLICY,
            SettingsEntry.AUTHOR,
            SettingsEntry.SOURCE_CODE,
            SettingsEntry.VERSION -> false
        }
    }

    override fun setBooleanValue(settingsEntry: SettingsEntry, value: Boolean) {
        when (settingsEntry) {
            SettingsEntry.UPPER_CASE,
            SettingsEntry.VIBRATION,
            SettingsEntry.EXPAND_HASH_FIELDS -> {
                sharedPreferences.edit { putBoolean(settingsEntry.key, value) }
            }

            SettingsEntry.FEEDBACK,
            SettingsEntry.PRIVACY_POLICY,
            SettingsEntry.AUTHOR,
            SettingsEntry.SOURCE_CODE,
            SettingsEntry.VERSION -> {
            }
        }
    }
}
