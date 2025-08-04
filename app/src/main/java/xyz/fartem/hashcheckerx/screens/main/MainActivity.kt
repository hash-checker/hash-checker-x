package xyz.fartem.hashcheckerx.screens.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import dagger.hilt.android.AndroidEntryPoint
import xyz.fartem.hashcheckerx.R
import xyz.fartem.hashcheckerx.core.clipboard.api.Clipboard
import xyz.fartem.hashcheckerx.core.logger.api.Logger
import xyz.fartem.hashcheckerx.core.vibrator.api.Vibrator
import xyz.fartem.hashcheckerx.core_ui.components.dialogs.HashCheckerXTextInputDialog
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.hash_generator.api.HashComparator
import xyz.fartem.hashcheckerx.hash_generator.api.HashGenerator
import xyz.fartem.hashcheckerx.hash_generator.model.HashType
import xyz.fartem.hashcheckerx.hash_generator.ui.HashGeneratorFieldFormat
import xyz.fartem.hashcheckerx.hash_generator.ui.HashGeneratorView
import xyz.fartem.hashcheckerx.hash_generator.ui.HashGeneratorViewCase
import xyz.fartem.hashcheckerx.hash_generator.ui.HashGeneratorViewModel
import xyz.fartem.hashcheckerx.hash_generator.ui.HashGeneratorViewModelFactory
import xyz.fartem.hashcheckerx.screens.settings.SettingsActivity
import xyz.fartem.hashcheckerx.settings.api.SettingsRepository
import xyz.fartem.hashcheckerx.settings.wrapper.SettingsWrapperView
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var hashGenerator: HashGenerator

    @Inject
    lateinit var hashComparator: HashComparator

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var vibrator: Vibrator

    @Inject
    lateinit var clipboard: Clipboard

    @Inject
    lateinit var settingsRepository: SettingsRepository

    private val hashGeneratorViewModel: HashGeneratorViewModel by viewModels {
        HashGeneratorViewModelFactory(
            hashGenerator = hashGenerator,
            hashComparator = hashComparator,
            logger = logger,
            defaultHashType = HashType.MD5,
        )
    }

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

    private var viewCase by mutableStateOf(HashGeneratorViewCase.LOWER)
    private var openSettings = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        recreate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SettingsWrapperView(settingsRepository) { settingsWrapper ->
                var selectText by remember { mutableStateOf(false) }

                HashCheckerXTheme {
                    Scaffold(
                        topBar = {
                            HashCheckerXTopBar(
                                title = stringResource(R.string.app_name),
                                actions = {
                                    IconButton(
                                        onClick = {
                                            openSettings.launch(
                                                Intent(
                                                    this@MainActivity,
                                                    SettingsActivity::class.java,
                                                )
                                            )
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Settings,
                                            contentDescription = stringResource(R.string.settings),
                                        )
                                    }
                                },
                            )
                        }
                    ) { innerPadding ->
                        HashGeneratorView(
                            viewModel = hashGeneratorViewModel,
                            viewCase = when (settingsWrapper.useUpperCase()) {
                                true -> HashGeneratorViewCase.UPPER
                                false -> HashGeneratorViewCase.LOWER
                            },
                            fieldFormat = when (settingsWrapper.useExpandedHashFields()) {
                                true -> HashGeneratorFieldFormat.EXPANDED
                                false -> HashGeneratorFieldFormat.SHORT
                            },
                            innerPadding = innerPadding,
                            onFileRequest = { selectFile.launch("*/*") },
                            onFolderRequest = { selectFolder.launch(null) },
                            onTextRequest = { selectText = true },
                            selectedFile = selectedFile,
                            selectedFolder = selectedFolder,
                            selectedText = selectedText,
                            onDone = {
                                if (settingsWrapper.useVibration()) {
                                    vibrator.oneShot()
                                }
                            },
                            onError = {
                                if (settingsWrapper.useVibration()) {
                                    vibrator.oneShot()
                                }
                            },
                            onCopy = {
                                clipboard.copy(it)
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
                                onDismiss = { selectText = false },
                            )
                        }
                    }
                }
            }
        }
    }
}
