package xyz.fartem.hashcheckerx.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.zhanghai.compose.preference.Preference
import me.zhanghai.compose.preference.PreferenceCategory
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import me.zhanghai.compose.preference.SwitchPreference
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXSurface
import xyz.fartem.hashcheckerx.settings.R
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository

@Composable
fun SettingsView(
    paddingValues: PaddingValues,
    settingsRepository: SettingsRepository,
) {
    ProvidePreferenceLocals {
        HashCheckerXSurface(paddingValues) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
            ) {
                PreferenceCategory(
                    title = {
                        Text("Hash Generator")
                    }
                )

                val keyUpperCase = stringResource(R.string.key_upper_case)
                var upperCaseValue by remember {
                    mutableStateOf(
                        settingsRepository.getBooleanValue(
                            key = keyUpperCase,
                            defaultValue = false,
                        )
                    )
                }

                SwitchPreference(
                    title = {
                        Text(stringResource(R.string.title_vibration))
                    },
                    summary = {
                        Text("Use upper case for all Generator fields")
                    },
                    value = settingsRepository.getBooleanValue(
                        key = keyUpperCase,
                        defaultValue = upperCaseValue,
                    ),
                    onValueChange = { upperCase ->
                        settingsRepository.setBooleanValue(
                            key = keyUpperCase,
                            value = upperCase
                        )

                        upperCaseValue = upperCase
                    },
                )

                PreferenceCategory(
                    title = {
                        Text("Application")
                    }
                )

                val keyVibration = stringResource(R.string.key_vibration)
                var vibrationValue by remember {
                    mutableStateOf(
                        settingsRepository.getBooleanValue(
                            key = keyVibration,
                            defaultValue = true,
                        )
                    )
                }

                SwitchPreference(
                    title = {
                        Text(stringResource(R.string.title_vibration))
                    },
                    summary = {
                        Text("Vibrate when hash was generated or will receive an error")
                    },
                    value = settingsRepository.getBooleanValue(
                        key = keyVibration,
                        defaultValue = vibrationValue,
                    ),
                    onValueChange = { vibration ->
                        settingsRepository.setBooleanValue(
                            key = keyVibration,
                            value = vibration
                        )

                        vibrationValue = vibration
                    },
                )

                PreferenceCategory(
                    title = {
                        Text("Privacy")
                    },
                )

                Preference(
                    title = {
                        Text(stringResource(R.string.title_privacy_policy))
                    },
                    summary = {
                        Text("Read about how app use your data")
                    }
                )

                PreferenceCategory(
                    title = {
                        Text("About")
                    },
                )

                Preference(
                    title = {
                        Text(stringResource(R.string.title_author))
                    },
                    summary = {
                        Text("fartem")
                    },
                )

                Preference(
                    title = {
                        Text(stringResource(R.string.title_source_code))
                    },
                    summary = {
                        Text("GitHub")
                    },
                )

                Preference(
                    title = {
                        Text(stringResource(R.string.title_version))
                    },
                    summary = {
                        Text("0.1.0")
                    },
                )
            }
        }
    }
}
