package xyz.fartem.hashcheckerx.settings.impl

import xyz.fartem.hashcheckerx.settings.R
import xyz.fartem.hashcheckerx.settings.api.SettingsWrapper

class SharedPreferencesSettingsWrapper(
    private val sharedPreferencesSettingsRepository: SharedPreferencesSettingsRepository,
    private val stringResourceProvider: (Int) -> String,
) : SettingsWrapper() {
    override fun upperCase(): Boolean {
        return sharedPreferencesSettingsRepository.getBooleanValue(
            stringResourceProvider(R.string.key_upper_case),
            false,
        )
    }

    override fun adaptiveTheme(): Boolean {
        return sharedPreferencesSettingsRepository.getBooleanValue(
            stringResourceProvider(R.string.key_adaptive_theme),
            true,
        )
    }

    override fun vibration(): Boolean {
        return sharedPreferencesSettingsRepository.getBooleanValue(
            stringResourceProvider(R.string.key_vibration),
            true,
        )
    }
}
