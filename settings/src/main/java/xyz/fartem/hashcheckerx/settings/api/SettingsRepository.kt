package xyz.fartem.hashcheckerx.settings.api

import xyz.fartem.hashcheckerx.settings.model.SettingsEntry

abstract class SettingsRepository {
    abstract fun getBooleanValue(settingsEntry: SettingsEntry): Boolean

    abstract fun setBooleanValue(settingsEntry: SettingsEntry, value: Boolean)
}
