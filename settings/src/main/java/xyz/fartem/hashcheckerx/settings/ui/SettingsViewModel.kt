package xyz.fartem.hashcheckerx.settings.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.fartem.hashcheckerx.settings.R
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.model.SettingsCategory
import xyz.fartem.hashcheckerx.settings.model.SettingsEntry

data class SettingsViewModelState(
    val settings: Map<SettingsCategory, List<Pair<SettingsEntry, Any?>>>,
    val privacyPolicy: String,
    val author: String,
    val sourceCode: String,
    val version: String,
)

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val stringResourcesProvider: (Int) -> String,
    privacyPolicy: String,
    author: String,
    sourceCode: String,
    version: String,
) : ViewModel() {
    private val _state = MutableStateFlow(
        SettingsViewModelState(
            settings = mapOf(
                SettingsCategory.GENERATOR to listOf(
                    Pair(
                        SettingsEntry.UPPER_CASE,
                        settingsRepository.getBooleanValue(
                            stringResourcesProvider(R.string.key_upper_case),
                            false,
                        ),
                    ),
                ),
                SettingsCategory.APPLICATION to listOf(
                    Pair(
                        SettingsEntry.ADAPTIVE_THEME,
                        settingsRepository.getBooleanValue(
                            stringResourcesProvider(R.string.key_adaptive_theme),
                            true,
                        ),
                    ),
                    Pair(
                        SettingsEntry.VIBRATION,
                        settingsRepository.getBooleanValue(
                            stringResourcesProvider(R.string.key_vibration),
                            true,
                        ),
                    ),
                ),
                SettingsCategory.PRIVACY to listOf(
                    Pair(SettingsEntry.PRIVACY_POLICY, null),
                ),
                SettingsCategory.ABOUT to listOf(
                    Pair(SettingsEntry.AUTHOR, null),
                    Pair(SettingsEntry.SOURCE_CODE, null),
                    Pair(SettingsEntry.VERSION, null),
                ),
            ),
            privacyPolicy = privacyPolicy,
            author = author,
            sourceCode = sourceCode,
            version = version,
        ),
    )
    val state = _state.asStateFlow()

    fun updateSettingsEntry(settingsEntry: SettingsEntry, value: Any) {
        when (settingsEntry) {
            SettingsEntry.UPPER_CASE -> {
                settingsRepository.setBooleanValue(
                    stringResourcesProvider(R.string.key_upper_case),
                    value as Boolean
                )
            }

            SettingsEntry.ADAPTIVE_THEME -> {
                settingsRepository.setBooleanValue(
                    stringResourcesProvider(R.string.key_adaptive_theme),
                    value as Boolean
                )
            }

            SettingsEntry.VIBRATION -> {
                settingsRepository.setBooleanValue(
                    stringResourcesProvider(R.string.key_vibration),
                    value as Boolean
                )
            }

            SettingsEntry.PRIVACY_POLICY -> {}
            SettingsEntry.AUTHOR -> {}
            SettingsEntry.SOURCE_CODE -> {}
            SettingsEntry.VERSION -> {}
        }
    }
}
