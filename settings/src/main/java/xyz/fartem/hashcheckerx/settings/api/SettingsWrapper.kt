package xyz.fartem.hashcheckerx.settings.api

import androidx.compose.runtime.Composable

abstract class SettingsWrapper {
    @Composable
    abstract fun vibration(): Boolean
}