package xyz.fartem.hashcheckerx.settings.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.zhanghai.compose.preference.Preference
import me.zhanghai.compose.preference.PreferenceCategory
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import me.zhanghai.compose.preference.SwitchPreference
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXSurface
import xyz.fartem.hashcheckerx.settings.R
import xyz.fartem.hashcheckerx.settings.model.SettingsCategory
import xyz.fartem.hashcheckerx.settings.model.SettingsEntry

@Composable
fun SettingsView(
    viewModel: SettingsViewModel,
    paddingValues: PaddingValues,
) {
    ProvidePreferenceLocals {
        HashCheckerXSurface(paddingValues) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
            ) {
                val state = viewModel.state.collectAsStateWithLifecycle().value

                state.settings.forEach { (category, entries) ->
                    PreferenceCategory(
                        title = {
                            Text(category.translatedName())
                        }
                    )

                    entries.forEach { entry ->
                        when (entry.first) {
                            SettingsEntry.UPPER_CASE -> {
                                SwitchPreference(
                                    title = {
                                        Text(stringResource(R.string.title_upper_case))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_upper_case))
                                    },
                                    value = entry.second as Boolean,
                                    onValueChange = { upperCase ->
                                        viewModel.updateSettingsEntry(entry.first, upperCase)
                                    },
                                )
                            }

                            SettingsEntry.ADAPTIVE_THEME -> {
                                SwitchPreference(
                                    title = {
                                        Text(stringResource(R.string.title_adaptive_theme))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_adaptive_theme))
                                    },
                                    value = entry.second as Boolean,
                                    onValueChange = { upperCase ->
                                        viewModel.updateSettingsEntry(entry.first, upperCase)
                                    },
                                )
                            }

                            SettingsEntry.VIBRATION -> {
                                SwitchPreference(
                                    title = {
                                        Text(stringResource(R.string.title_vibration))
                                    },
                                    summary = {
                                        Text(stringResource(R.string.description_vibration))
                                    },
                                    value = entry.second as Boolean,
                                    onValueChange = { upperCase ->
                                        viewModel.updateSettingsEntry(entry.first, upperCase)
                                    },
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
                                    onClick = {},
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
                                    onClick = {},
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
                                    onClick = {},
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
                                    onClick = {},
                                )
                            }
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
        SettingsCategory.PRIVACY -> stringResource(R.string.category_privacy)
        SettingsCategory.ABOUT -> stringResource(R.string.category_about)
    }
}
