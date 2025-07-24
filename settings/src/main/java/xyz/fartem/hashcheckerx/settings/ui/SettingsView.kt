package xyz.fartem.hashcheckerx.settings.ui

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    openPrivacyPolicy: () -> Unit,
    author: String,
    openAuthor: () -> Unit,
    openSourceCode: () -> Unit,
    version: String,
    openVersion: () -> Unit,
) {
    ProvidePreferenceLocals {
        HashCheckerXSurface(paddingValues) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
            ) {
                PreferenceCategory(
                    title = {
                        Text(stringResource(R.string.category_generator))
                    }
                )

                val keyUpperCase = stringResource(R.string.key_upper_case)
                var valueUpperCase by remember {
                    mutableStateOf(
                        settingsRepository.getBooleanValue(
                            key = keyUpperCase,
                            defaultValue = false,
                        )
                    )
                }

                SwitchPreference(
                    title = {
                        Text(stringResource(R.string.title_upper_case))
                    },
                    summary = {
                        Text(stringResource(R.string.description_upper_case))
                    },
                    value = settingsRepository.getBooleanValue(
                        key = keyUpperCase,
                        defaultValue = valueUpperCase,
                    ),
                    onValueChange = { upperCase ->
                        settingsRepository.setBooleanValue(
                            key = keyUpperCase,
                            value = upperCase
                        )

                        valueUpperCase = upperCase
                    },
                )

                PreferenceCategory(
                    title = {
                        Text(stringResource(R.string.category_application))
                    }
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val keyAdaptiveTheme = stringResource(R.string.key_adaptive_theme)
                    var valueAdaptiveTheme by remember {
                        mutableStateOf(
                            settingsRepository.getBooleanValue(
                                key = keyAdaptiveTheme,
                                defaultValue = true,
                            )
                        )
                    }

                    SwitchPreference(
                        title = {
                            Text(stringResource(R.string.title_adaptive_theme))
                        },
                        summary = {
                            Text(stringResource(R.string.description_adaptive_theme))
                        },
                        value = settingsRepository.getBooleanValue(
                            key = keyAdaptiveTheme,
                            defaultValue = valueAdaptiveTheme,
                        ),
                        onValueChange = { vibration ->
                            settingsRepository.setBooleanValue(
                                key = keyAdaptiveTheme,
                                value = vibration
                            )

                            valueAdaptiveTheme = vibration
                        },
                    )
                }

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
                        Text(stringResource(R.string.description_vibration))
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
                        Text(stringResource(R.string.category_privacy))
                    },
                )

                Preference(
                    title = {
                        Text(stringResource(R.string.title_privacy_policy))
                    },
                    summary = {
                        Text(stringResource(R.string.description_privacy))
                    },
                    onClick = {
                        openPrivacyPolicy.invoke()
                    },
                )

                PreferenceCategory(
                    title = {
                        Text(stringResource(R.string.category_about))
                    },
                )

                Preference(
                    title = {
                        Text(stringResource(R.string.title_author))
                    },
                    summary = {
                        Text(author)
                    },
                    onClick = {
                        openAuthor.invoke()
                    },
                )

                Preference(
                    title = {
                        Text(stringResource(R.string.title_source_code))
                    },
                    summary = {
                        Text(stringResource(R.string.description_source_code))
                    },
                    onClick = {
                        openSourceCode.invoke()
                    },
                )

                Preference(
                    title = {
                        Text(stringResource(R.string.title_version))
                    },
                    summary = {
                        Text(version)
                    },
                    onClick = {
                        openVersion.invoke()
                    },
                )
            }
        }
    }
}
