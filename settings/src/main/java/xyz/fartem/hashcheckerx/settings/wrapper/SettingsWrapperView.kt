package xyz.fartem.hashcheckerx.settings.wrapper

import androidx.compose.runtime.Composable
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository

@Composable
fun SettingsWrapperView(
    settingsRepository: SettingsRepository,
    ui: @Composable (SettingsWrapper) -> Unit,
) {
    val settingsWrapper = SettingsWrapper(settingsRepository)

    ui.invoke(settingsWrapper)
}
