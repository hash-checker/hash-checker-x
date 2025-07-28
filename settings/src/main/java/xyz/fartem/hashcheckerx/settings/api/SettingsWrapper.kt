package xyz.fartem.hashcheckerx.settings.api

abstract class SettingsWrapper {
    abstract fun upperCase(): Boolean

    abstract fun adaptiveTheme(): Boolean

    abstract fun vibration(): Boolean
}
