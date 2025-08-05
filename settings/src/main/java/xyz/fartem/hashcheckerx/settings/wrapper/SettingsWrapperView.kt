package xyz.fartem.hashcheckerx.settings.wrapper

import androidx.compose.runtime.Composable

@Composable
fun SettingsWrapperView(
    settingsWrapper: SettingsWrapper,
    ui: @Composable (SettingsWrapper) -> Unit,
) {
    ui.invoke(settingsWrapper)
}
