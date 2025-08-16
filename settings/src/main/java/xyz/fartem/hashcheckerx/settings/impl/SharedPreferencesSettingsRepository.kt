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
            SettingsEntry.EXPAND_HASH_FIELDS,
            SettingsEntry.HISTORY,
            SettingsEntry.VIBRATION -> {
                sharedPreferences.getBoolean(
                    settingsEntry.key,
                    settingsEntry.defaultValue as Boolean,
                )
            }

            SettingsEntry.FEEDBACK,
            SettingsEntry.PRIVACY_POLICY,
            SettingsEntry.AUTHOR,
            SettingsEntry.SOURCE_CODE,
            SettingsEntry.VERSION,
            SettingsEntry.HASH_TYPE -> false
        }
    }

    override fun setBooleanValue(settingsEntry: SettingsEntry, value: Boolean) {
        when (settingsEntry) {
            SettingsEntry.UPPER_CASE,
            SettingsEntry.EXPAND_HASH_FIELDS,
            SettingsEntry.HISTORY,
            SettingsEntry.VIBRATION -> {
                sharedPreferences.edit { putBoolean(settingsEntry.key, value) }
            }

            SettingsEntry.FEEDBACK,
            SettingsEntry.PRIVACY_POLICY,
            SettingsEntry.AUTHOR,
            SettingsEntry.SOURCE_CODE,
            SettingsEntry.VERSION,
            SettingsEntry.HASH_TYPE -> {
            }
        }
    }

    override fun getStringValue(settingsEntry: SettingsEntry): String? {
        return when (settingsEntry) {
            SettingsEntry.HASH_TYPE -> {
                sharedPreferences.getString(
                    settingsEntry.key,
                    settingsEntry.defaultValue as String?,
                )
            }

            SettingsEntry.UPPER_CASE,
            SettingsEntry.HISTORY,
            SettingsEntry.VIBRATION,
            SettingsEntry.EXPAND_HASH_FIELDS,
            SettingsEntry.FEEDBACK,
            SettingsEntry.PRIVACY_POLICY,
            SettingsEntry.AUTHOR,
            SettingsEntry.SOURCE_CODE,
            SettingsEntry.VERSION -> null
        }
    }

    override fun setStringValue(settingsEntry: SettingsEntry, value: String) {
        when (settingsEntry) {
            SettingsEntry.HASH_TYPE -> {
                sharedPreferences.edit { putString(settingsEntry.key, value) }
            }

            SettingsEntry.UPPER_CASE,
            SettingsEntry.HISTORY,
            SettingsEntry.VIBRATION,
            SettingsEntry.EXPAND_HASH_FIELDS,
            SettingsEntry.FEEDBACK,
            SettingsEntry.PRIVACY_POLICY,
            SettingsEntry.AUTHOR,
            SettingsEntry.SOURCE_CODE,
            SettingsEntry.VERSION -> {
            }
        }
    }
}
