package xyz.fartem.hashcheckerx.screens.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import xyz.fartem.hashcheckerx.BuildConfig
import xyz.fartem.hashcheckerx.R
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.ui.SettingsView
import xyz.fartem.hashcheckerx.settings.ui.SettingsViewModel
import xyz.fartem.hashcheckerx.settings.ui.SettingsViewModelFactory
import xyz.fartem.hashcheckerx.settings.wrapper.SettingsWrapperView
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {
    @Inject
    lateinit var settingsRepository: SettingsRepository

    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(
            settingsRepository = settingsRepository,
            privacyPolicy = "",
            author = "fartem",
            sourceCode = BuildConfig.URL_SOURCE_CODE,
            version = BuildConfig.VERSION_NAME,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SettingsWrapperView(settingsRepository) { settingsWrapper ->
                HashCheckerXTheme {
                    Scaffold(
                        topBar = {
                            HashCheckerXTopBar(
                                title = stringResource(R.string.settings),
                                navigationIcon = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                            contentDescription = "Localized description",
                                        )
                                    }
                                },
                            )
                        },
                    ) { paddingValues ->
                        SettingsView(
                            viewModel = settingsViewModel,
                            paddingValues = paddingValues,
                        )
                    }
                }
            }
        }
    }
}
