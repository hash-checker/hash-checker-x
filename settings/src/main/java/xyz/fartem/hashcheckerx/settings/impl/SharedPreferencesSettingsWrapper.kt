package xyz.fartem.hashcheckerx.settings.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.fartem.hashcheckerx.settings.R
import xyz.fartem.hashcheckerx.settings.api.SettingsWrapper

class SharedPreferencesSettingsWrapper(
    private val sharedPreferencesSettingsRepository: SharedPreferencesSettingsRepository
) : SettingsWrapper() {
    @Composable
    override fun vibration(): Boolean {
        return sharedPreferencesSettingsRepository.getBooleanValue(
            stringResource(R.string.key_vibration),
            true,
        )
    }
}
