package xyz.fartem.hashcheckerx

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import xyz.fartem.hashcheckerx.core.vibrator.Vibrator
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXTextInputDialog
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashComparator
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import xyz.fartem.hashcheckerx.hash_generator.ui.HashGeneratorView
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsRepository
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsWrapper


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val hashGenerator = JdkHashGenerator()
        hashGenerator.setHashType(HashType.MD5)

        val vibrator = Vibrator(this)

        val settings = SharedPreferencesSettingsWrapper(
            SharedPreferencesSettingsRepository(
                getPreferences(Context.MODE_PRIVATE)
            )
        )

        setContent {
            var selectText by remember { mutableStateOf(false) }
            var selectedText by remember { mutableStateOf<String?>(null) }

            HashCheckerXTheme {
                Scaffold(
                    topBar = {
                        HashCheckerXTopBar(
                            title = stringResource(R.string.app_name),
                            actions = {
                                IconButton(
                                    onClick = {
                                        startActivity(
                                            Intent(
                                                this@MainActivity,
                                                SettingsActivity::class.java,
                                            )

                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Settings,
                                        contentDescription = stringResource(R.string.settings)
                                    )
                                }
                            },
                        )
                    }
                ) { innerPadding ->
                    HashGeneratorView(
                        hashGenerator = hashGenerator,
                        hashComparator = JdkHashComparator(),
                        defaultHashType = HashType.MD5,
                        innerPadding = innerPadding,
                        onFileRequest = {},
                        onFolderRequest = {},
                        onTextRequest = { selectText = true },
                        selectedFile = null,
                        selectedFolder = null,
                        selectedText = selectedText,
                        onDone = {
                            if (settings.vibration()) {
                                vibrator.oneShot()
                            }
                        },
                        onError = {
                            if (settings.vibration()) {
                                vibrator.oneShot()
                            }
                        },
                    )

                    if (selectText) {
                        HashCheckerXTextInputDialog(
                            initialText = if (selectedText.isNullOrEmpty()) "" else selectedText!!,
                            onConfirm = { text ->
                                selectedText = text
                                selectText = false
                            },
                            onDismiss = { selectText = false }
                        )
                    }

                }
            }
        }
    }
}