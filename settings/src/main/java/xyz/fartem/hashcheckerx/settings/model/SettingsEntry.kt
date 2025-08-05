package xyz.fartem.hashcheckerx.settings.model

enum class SettingsEntry(val key: String, val defaultValue: Any?) {
    UPPER_CASE("pref_upper_case", false),
    EXPAND_HASH_FIELDS("pref_expand_hash_fields", false),
    VIBRATION("pref_vibration", true),
    FEEDBACK("pref_feedback", null),
    PRIVACY_POLICY("prev_privacy_policy", null),
    AUTHOR("prev_author", null),
    SOURCE_CODE("pref_source_code", null),
    VERSION("pref_version", null),

    HASH_TYPE("pref_hash_type", null),
}
