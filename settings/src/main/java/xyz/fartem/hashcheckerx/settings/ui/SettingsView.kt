package xyz.fartem.hashcheckerx.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.zhanghai.compose.preference.Preference
import me.zhanghai.compose.preference.PreferenceCategory
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import me.zhanghai.compose.preference.SwitchPreference
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXSurface
import xyz.fartem.hashcheckerx.settings.R
import xyz.fartem.hashcheckerx.settings.model.SettingsCategory
import xyz.fartem.hashcheckerx.settings.model.SettingsEntry

@Composable
fun SettingsView(
    viewModel: SettingsViewModel,
    paddingValues: PaddingValues,
    onFeedbackRequest: () -> Unit,
    onPrivacyPolicyRequest: () -> Unit,
    onAuthorRequest: () -> Unit,
    onSourceCodeRequest: () -> Unit,
    onVersionRequest: () -> Unit,
) {
    ProvidePreferenceLocals {
        HashCheckerXSurface(paddingValues) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
            ) {
                val state = viewModel.state.collectAsStateWithLifecycle().value

                state.settings.forEach { (category, entries) ->
                    PreferenceCategory(
                        title = {
                            Text(category.translatedName())
                        },
                    )

                    entries.forEach { entry ->
                        when (entry.first) {
                            SettingsEntry.UPPER_CASE -> {
                                var value by remember { mutableStateOf(entry.second as Boolean) }

                                SwitchPreference(
                                    title = {
                                        Text(stringResource(R.string.title_upper_case))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_upper_case))
                                    },
                                    value = value,
                                    onValueChange = { upperCase ->
                                        viewModel.updateSettingsEntry(entry.first, upperCase)

                                        value = upperCase
                                    },
                                )
                            }

                            SettingsEntry.EXPAND_HASH_FIELDS -> {
                                var value by remember { mutableStateOf(entry.second as Boolean) }

                                SwitchPreference(
                                    title = {
                                        Text(stringResource(R.string.title_expand_hash_fields))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_expand_hash_fields))
                                    },
                                    value = value,
                                    onValueChange = { expandHashFields ->
                                        viewModel.updateSettingsEntry(entry.first, expandHashFields)

                                        value = expandHashFields
                                    },
                                )
                            }

                            SettingsEntry.HISTORY -> {
                                var value by remember { mutableStateOf(entry.second as Boolean) }

                                SwitchPreference(
                                    title = {
                                        Text(stringResource(R.string.title_history))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_history))
                                    },
                                    value = value,
                                    onValueChange = { history ->
                                        viewModel.updateSettingsEntry(entry.first, history)

                                        value = history
                                    },
                                )
                            }

                            SettingsEntry.VIBRATION -> {
                                var value by remember { mutableStateOf(entry.second as Boolean) }

                                SwitchPreference(
                                    title = {
                                        Text(stringResource(R.string.title_vibration))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_vibration))
                                    },
                                    value = value,
                                    onValueChange = { vibration ->
                                        viewModel.updateSettingsEntry(entry.first, vibration)

                                        value = vibration
                                    },
                                )
                            }

                            SettingsEntry.FEEDBACK -> {
                                Preference(
                                    title = {
                                        Text(stringResource(R.string.title_feedback))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_feedback))
                                    },
                                    onClick = { onFeedbackRequest.invoke() },
                                )
                            }

                            SettingsEntry.PRIVACY_POLICY -> {
                                Preference(
                                    title = {
                                        Text(stringResource(R.string.title_privacy_policy))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_privacy))
                                    },
                                    onClick = { onPrivacyPolicyRequest.invoke() },
                                )
                            }

                            SettingsEntry.AUTHOR -> {
                                Preference(
                                    title = {
                                        Text(stringResource(R.string.title_author))
                                    },
                                    summary = {
                                        Text(state.author)
                                    },
                                    onClick = { onAuthorRequest.invoke() },
                                )
                            }

                            SettingsEntry.SOURCE_CODE -> {
                                Preference(
                                    title = {
                                        Text(stringResource(R.string.title_source_code))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_source_code))
                                    },
                                    onClick = { onSourceCodeRequest.invoke() },
                                )
                            }

                            SettingsEntry.VERSION -> {
                                Preference(
                                    title = {
                                        Text(stringResource(R.string.title_version))
                                    },
                                    summary = {
                                        Text(state.version)
                                    },
                                    onClick = { onVersionRequest.invoke() },
                                )
                            }

                            SettingsEntry.HASH_TYPE -> {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsCategory.translatedName(): String {
    return when (this) {
        SettingsCategory.GENERATOR -> stringResource(R.string.category_generator)
        SettingsCategory.APPLICATION -> stringResource(R.string.category_application)
        SettingsCategory.SUPPORT -> stringResource(R.string.category_support)
        SettingsCategory.PRIVACY -> stringResource(R.string.category_privacy)
        SettingsCategory.ABOUT -> stringResource(R.string.category_about)
    }
}
