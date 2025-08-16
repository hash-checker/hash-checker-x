package xyz.fartem.hashcheckerx.settings.wrapper

import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.model.SettingsEntry

class SettingsWrapper(private val settingsRepository: SettingsRepository) {
    fun useUpperCase(): Boolean {
        return settingsRepository.getBooleanValue(SettingsEntry.UPPER_CASE)
    }

    fun useExpandedHashFields(): Boolean {
        return settingsRepository.getBooleanValue(SettingsEntry.EXPAND_HASH_FIELDS)
    }

    fun useHistory(): Boolean {
        return settingsRepository.getBooleanValue(SettingsEntry.HISTORY)
    }

    fun useVibration(): Boolean {
        return settingsRepository.getBooleanValue(SettingsEntry.VIBRATION)
    }

    fun getHashType(defaultHashType: HashType): HashType {
        val savedHashType = settingsRepository.getStringValue(SettingsEntry.HASH_TYPE)

        if (savedHashType == null) {
            return defaultHashType
        }

        return HashType.entries.first { it.name == savedHashType }
    }

    fun setHashType(hashType: HashType) {
        settingsRepository.setStringValue(SettingsEntry.HASH_TYPE, hashType.name)
    }
}
