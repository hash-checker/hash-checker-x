package xyz.fartem.hashcheckerx

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsRepository
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsWrapper
import xyz.fartem.hashcheckerx.settings.ui.SettingsView

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settings = SharedPreferencesSettingsWrapper(
            SharedPreferencesSettingsRepository(
                getSharedPreferences(
                    BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE
                )
            )
        )

        setContent {
            HashCheckerXTheme(
                dynamicColor = settings.adaptiveTheme(),
            ) {
                Scaffold(
                    topBar = {
                        HashCheckerXTopBar(
                            title = stringResource(R.string.settings),
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                        )
                    },
                ) { paddingValues ->
                    SettingsView(
                        settingsRepository = SharedPreferencesSettingsRepository(
                            getSharedPreferences(
                                BuildConfig.APPLICATION_ID,
                                Context.MODE_PRIVATE
                            )
                        ),
                        paddingValues = paddingValues,
                        openPrivacyPolicy = {},
                        author = "fartem",
                        openAuthor = {
                            launchUrl(BuildConfig.URL_AUTHOR)
                        },
                        openSourceCode = {
                            launchUrl(BuildConfig.URL_SOURCE_CODE)
                        },
                        version = BuildConfig.VERSION_NAME,
                        openVersion = {
                            launchUrl("${BuildConfig.URL_RELEASES}${BuildConfig.VERSION_NAME}")
                        },
                    )
                }
            }
        }
    }

    private fun launchUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, url.toUri())

        startActivity(browserIntent)
    }
}
