package xyz.fartem.hashcheckerx

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsRepository
import xyz.fartem.hashcheckerx.settings.ui.SettingsView

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HashCheckerXTheme {
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
                            getPreferences(Context.MODE_PRIVATE)
                        ),
                        paddingValues = paddingValues,
                    )
                }
            }
        }
    }
}