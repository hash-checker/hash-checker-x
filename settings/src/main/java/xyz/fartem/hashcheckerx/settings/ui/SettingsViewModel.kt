package xyz.fartem.hashcheckerx.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.model.SettingsCategory
import xyz.fartem.hashcheckerx.settings.model.SettingsEntry

data class SettingsViewModelState(
    val settings: Map<SettingsCategory, List<Pair<SettingsEntry, Any?>>>,
    val author: String,
    val sourceCode: String,
    val version: String,
)

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
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
                            SettingsEntry.UPPER_CASE,
                        ),
                    ),
                    Pair(
                        SettingsEntry.EXPAND_HASH_FIELDS,
                        settingsRepository.getBooleanValue(
                            SettingsEntry.EXPAND_HASH_FIELDS,
                        ),
                    ),
                ),
                SettingsCategory.APPLICATION to listOf(
                    Pair(
                        SettingsEntry.VIBRATION,
                        settingsRepository.getBooleanValue(
                            SettingsEntry.VIBRATION,
                        ),
                    ),
                ),
                SettingsCategory.SUPPORT to listOf(
                    Pair(SettingsEntry.FEEDBACK, null),
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
            author = author,
            sourceCode = sourceCode,
            version = version,
        ),
    )
    val state = _state.asStateFlow()

    fun updateSettingsEntry(settingsEntry: SettingsEntry, value: Any) {
        when (settingsEntry) {
            SettingsEntry.UPPER_CASE,
            SettingsEntry.VIBRATION,
            SettingsEntry.EXPAND_HASH_FIELDS -> {
                settingsRepository.setBooleanValue(
                    settingsEntry,
                    value as Boolean
                )
            }

            SettingsEntry.FEEDBACK -> {}
            SettingsEntry.PRIVACY_POLICY -> {}
            SettingsEntry.AUTHOR -> {}
            SettingsEntry.SOURCE_CODE -> {}
            SettingsEntry.VERSION -> {}
            SettingsEntry.HASH_TYPE -> {}
        }
    }
}

class SettingsViewModelFactory(
    private val settingsRepository: SettingsRepository,
    private val author: String,
    private val sourceCode: String,
    private val version: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                settingsRepository = settingsRepository,
                author = author,
                sourceCode = sourceCode,
                version = version,
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
