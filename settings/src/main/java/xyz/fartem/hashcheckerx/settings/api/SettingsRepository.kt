package xyz.fartem.hashcheckerx.settings.api

import xyz.fartem.hashcheckerx.settings.model.SettingsEntry

abstract class SettingsRepository {
    abstract fun getBooleanValue(settingsEntry: SettingsEntry): Boolean

    abstract fun setBooleanValue(settingsEntry: SettingsEntry, value: Boolean)

    abstract fun getStringValue(settingsEntry: SettingsEntry): String?

    abstract fun setStringValue(settingsEntry: SettingsEntry, value: String)
}
