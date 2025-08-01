package xyz.fartem.hashcheckerx.settings.wrapper

import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.model.SettingsEntry

class SettingsWrapper(private val settingsRepository: SettingsRepository) {
    fun useUpperCase(): Boolean {
        return settingsRepository.getBooleanValue(SettingsEntry.UPPER_CASE)
    }

    fun useVibration(): Boolean {
        return settingsRepository.getBooleanValue(SettingsEntry.VIBRATION)
    }
}
