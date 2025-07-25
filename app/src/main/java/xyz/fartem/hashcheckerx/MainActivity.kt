package xyz.fartem.hashcheckerx

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import xyz.fartem.hashcheckerx.core.vibrator.Vibrator
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXProgressIndicator
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXTextInputDialog
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashComparator
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import xyz.fartem.hashcheckerx.hash_generator.ui.HashGeneratorView
import xyz.fartem.hashcheckerx.hash_generator.ui.HashGeneratorViewCase
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsRepository
import xyz.fartem.hashcheckerx.settings.impl.SharedPreferencesSettingsWrapper

class MainActivity : ComponentActivity() {
    private var selectedFile by mutableStateOf<Uri?>(null)
    private val selectFile = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedFile = uri

        selectedFolder = null
        selectedText = null
    }

    private var selectedFolder by mutableStateOf<Uri?>(null)
    private val selectFolder = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        selectedFolder = uri

        selectedFile = null
        selectedText = null
    }

    private var selectedText by mutableStateOf<String?>(null)

    private var showProgressIndicator by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val hashGenerator = JdkHashGenerator()
        hashGenerator.setHashType(HashType.MD5)

        val vibrator = Vibrator(this)

        val settings = SharedPreferencesSettingsWrapper(
            SharedPreferencesSettingsRepository(
                getSharedPreferences(
                    BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE
                )
            )
        )

        setContent {
            var selectText by remember { mutableStateOf(false) }

            HashCheckerXTheme(
                dynamicColor = settings.adaptiveTheme(),
            ) {
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
                    val case = if (settings.upperCase()) HashGeneratorViewCase.UPPER else HashGeneratorViewCase.LOWER

                    HashGeneratorView(
                        hashGenerator = hashGenerator,
                        hashComparator = JdkHashComparator(),
                        defaultHashType = HashType.MD5,
                        innerPadding = innerPadding,
                        hashGeneratorViewCase = case,
                        onFileRequest = { selectFile.launch("*/*") },
                        onFolderRequest = { selectFolder.launch(null) },
                        onTextRequest = { selectText = true },
                        selectedFile = selectedFile,
                        selectedFolder = selectedFolder,
                        selectedText = selectedText,
                        onGenerationStart = { showProgressIndicator = true },
                        onGenerationEnd = { showProgressIndicator = false },
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

                                selectedFile = null
                                selectedFolder = null
                            },
                            onDismiss = { selectText = false }
                        )
                    }

                    if (showProgressIndicator) {
                        HashCheckerXProgressIndicator()
                    }
                }
            }
        }
    }
}
