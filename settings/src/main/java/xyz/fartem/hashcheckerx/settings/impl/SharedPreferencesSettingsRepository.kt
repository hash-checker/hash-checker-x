package xyz.fartem.hashcheckerx.settings.impl

import android.content.SharedPreferences
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import androidx.core.content.edit

class SharedPreferencesSettingsRepository(
    private val sharedPreferences: SharedPreferences,
) : SettingsRepository() {
    override fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun setBooleanValue(key: String, value: Boolean) {
//        sharedPreferences.edit { putBoolean(key, value) }
    }
}
