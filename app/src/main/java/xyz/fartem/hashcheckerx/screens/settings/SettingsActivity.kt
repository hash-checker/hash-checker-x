package xyz.fartem.hashcheckerx.screens.settings

import android.content.Context
import android.content.Intent
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
import androidx.core.net.toUri
import dagger.hilt.android.AndroidEntryPoint
import xyz.fartem.hashcheckerx.BuildConfig
import xyz.fartem.hashcheckerx.R
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.components.toasts.showHashCheckerXToast
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.screens.feedback.FeedbackActivity
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
                            onFeedbackRequest = {
                                startActivity(
                                    Intent(
                                        this@SettingsActivity,
                                        FeedbackActivity::class.java,
                                    )
                                )
                            },
                            onPrivacyPolicyRequest = {
                                tryOpenUrl(this, BuildConfig.URL_PRIVACY_POLICY)
                            },
                            onAuthorRequest = {
                                tryOpenUrl(this, BuildConfig.URL_AUTHOR)
                            },
                            onSourceCodeRequest = {
                                tryOpenUrl(this, BuildConfig.URL_SOURCE_CODE)
                            },
                            onVersionRequest = {
                                tryOpenUrl(this, BuildConfig.URL_RELEASES)
                            },
                        )
                    }
                }
            }
        }
    }

    private fun tryOpenUrl(context: Context, url: String) {
        try {
            val privacyPolicyIntent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(
                Intent.createChooser(
                    privacyPolicyIntent,
                    "Send mail..."
                ).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
            )
        } catch (_: Exception) {
            showHashCheckerXToast(context, getString(R.string.error_privacy_policy))
        }
    }
}
