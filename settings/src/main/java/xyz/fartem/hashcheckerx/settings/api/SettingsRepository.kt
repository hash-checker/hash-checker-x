package xyz.fartem.hashcheckerx.settings.api

abstract class SettingsRepository {
    abstract fun getBooleanValue(key: String, defaultValue: Boolean): Boolean

    abstract fun setBooleanValue(key: String, value: Boolean)
}