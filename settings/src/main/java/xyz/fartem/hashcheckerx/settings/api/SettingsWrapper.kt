package xyz.fartem.hashcheckerx.settings.api

import androidx.compose.runtime.Composable

abstract class SettingsWrapper {
    @Composable
    abstract fun upperCase(): Boolean

    @Composable
    abstract fun adaptiveTheme(): Boolean

    @Composable
    abstract fun vibration(): Boolean
}
