package xyz.fartem.hashcheckerx.settings.model

enum class SettingsEntry(val key: String, val defaultValue: Any?) {
    UPPER_CASE("pref_upper_case", false),
    VIBRATION("pref_vibration", true),
    PRIVACY_POLICY("prev_privacy_policy", null),
    AUTHOR("prev_author", null),
    SOURCE_CODE("pref_source_code", null),
    VERSION("pref_version", null),
}
