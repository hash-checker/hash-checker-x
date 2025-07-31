package xyz.fartem.hashcheckerx.hash_generator.ui

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Compare
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.TextFormat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.fartem.hashcheckerx.core.logger.impl.OrhanObutLogger
import xyz.fartem.hashcheckerx.core_ui.components.bottomsheet.HashCheckerXBottomSheet
import xyz.fartem.hashcheckerx.core_ui.components.buttons.HashCheckerXButton
import xyz.fartem.hashcheckerx.core_ui.components.text.HashCheckerXHint
import xyz.fartem.hashcheckerx.core_ui.components.bottomsheet.HashCheckerXListItem
import xyz.fartem.hashcheckerx.core_ui.components.dialogs.HashCheckerXProgressIndicator
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer16W
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer32H
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer4H
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer64H
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer8H
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXSurface
import xyz.fartem.hashcheckerx.core_ui.components.buttons.HashCheckerXTextButton
import xyz.fartem.hashcheckerx.core_ui.components.text.HashCheckerXTextField
import xyz.fartem.hashcheckerx.core_ui.components.toasts.showHashCheckerXToast
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.hash_generator.R
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashComparator
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.model.HashAction
import xyz.fartem.hashcheckerx.hash_generator.model.HashSource
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HashGeneratorView(
    viewModel: HashGeneratorViewModel,
    viewCase: HashGeneratorViewCase,
    innerPadding: PaddingValues,
    onFileRequest: () -> Unit,
    onFolderRequest: () -> Unit,
    onTextRequest: () -> Unit,
    selectedFile: Uri?,
    selectedFolder: Uri?,
    selectedText: String?,
    onDone: () -> Unit,
    onError: () -> Unit,
    onCopy: (String) -> Unit,
) {
    val context = LocalContext.current

    var showTypeSelector by remember { mutableStateOf(false) }
    var showSourceSelector by remember { mutableStateOf(false) }
    var showActionSelector by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel, LocalLifecycleOwner.current.lifecycle) {
        viewModel.events.collect { event ->
            when (event) {
                is HashGeneratorEvent.Comparison -> {
                    if (event.result) {
                        showHashCheckerXToast(
                            context,
                            context.getString(R.string.hash_generator_equals)
                        )

                        onDone.invoke()
                    } else {
                        showHashCheckerXToast(
                            context,
                            context.getString(R.string.hash_generator_different)
                        )

                        onError.invoke()
                    }
                }

                is HashGeneratorEvent.ComparisonError -> {
                    showHashCheckerXToast(
                        context,
                        context.getString(R.string.hash_comparator_error)
                    )

                    onError.invoke()
                }

                is HashGeneratorEvent.CustomHashEmptyError -> {
                    showHashCheckerXToast(
                        context,
                        context.getString(R.string.hash_generator_custom_hash_empty)
                    )

                    onError.invoke()
                }

                is HashGeneratorEvent.GeneratedHashEmptyError -> {
                    showHashCheckerXToast(
                        context,
                        context.getString(R.string.hash_generator_generated_hash_empty)
                    )

                    onError.invoke()
                }

                is HashGeneratorEvent.GenerationError -> {
                    showHashCheckerXToast(
                        context,
                        context.getString(R.string.hash_generator_error)
                    )

                    onError.invoke()
                }

                is HashGeneratorEvent.GenerationFromFileError -> {
                    showHashCheckerXToast(
                        context,
                        context.getString(R.string.hash_generator_error)
                    )

                    onError.invoke()
                }

                is HashGeneratorEvent.GenerationFromFolderError -> {
                    showHashCheckerXToast(
                        context,
                        context.getString(R.string.hash_generator_error)
                    )

                    onError.invoke()
                }

                is HashGeneratorEvent.GenerationFromTextError -> {
                    showHashCheckerXToast(
                        context,
                        context.getString(R.string.hash_generator_error)
                    )

                    onError.invoke()
                }
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value

    HashCheckerXSurface(innerPadding) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HashCheckerXTextButton(state.hashType.translatedName()) {
                showTypeSelector = true
            }

            if (showTypeSelector) {
                HashCheckerXBottomSheet(
                    onDismissRequest = { showTypeSelector = false },
                ) {
                    Column {
                        HashType.entries.map {
                            HashCheckerXListItem(
                                it.translatedName(),
                                icon = if (it == state.hashType) Icons.AutoMirrored.Rounded.ArrowForward else null,
                                iconDescription = "${stringResource(R.string.description_hash_type)} = ${it.translatedName()}",
                            ) {
                                showTypeSelector = false

                                viewModel.onHashTypeSelected(it)
                            }
                        }

                        HashCheckerXSpacer64H()
                    }
                }
            }

            if (state.isGenerating) {
                HashCheckerXProgressIndicator()
            }

            HashCheckerXSpacer8H()

            HashCheckerXTextField(
                text = when (viewCase) {
                    HashGeneratorViewCase.UPPER -> state.customHash.uppercase()
                    else -> state.customHash
                },
                label = stringResource(R.string.hash_generator_custom_hash),
                onValueChange = viewModel::onCustomHashChange,
                onCopy = { onCopy.invoke(state.customHash) },
                onClear = { viewModel.onCustomHashChange("") },
            )

            HashCheckerXSpacer4H()

            HashCheckerXTextField(
                text = when (viewCase) {
                    HashGeneratorViewCase.UPPER -> state.generatedHash.uppercase()
                    else -> state.generatedHash
                },
                label = stringResource(R.string.hash_generator_generator_hash),
                onValueChange = viewModel::onGeneratedHashChanged,
                onCopy = { onCopy.invoke(state.generatedHash) },
                onClear = { viewModel.onGeneratedHashChanged("") },
            )

            HashCheckerXSpacer32H()

            Row {
                HashCheckerXButton(stringResource(R.string.hash_generator_from)) {
                    showSourceSelector = true
                }

                if (showSourceSelector) {
                    HashCheckerXBottomSheet({ showSourceSelector = false }) {
                        Column {
                            HashSource.entries.map {
                                HashCheckerXListItem(
                                    it.translatedName(),
                                    icon = when (it) {
                                        HashSource.FILE -> Icons.Rounded.AttachFile
                                        HashSource.FOLDER -> Icons.Rounded.Folder
                                        HashSource.TEXT -> Icons.Rounded.TextFormat
                                    },
                                    iconDescription = "${stringResource(R.string.description_hash_source)} = ${it.translatedName()}"
                                ) {
                                    viewModel.onHashSourceSelected(it)

                                    when (it) {
                                        HashSource.FILE -> onFileRequest.invoke()
                                        HashSource.FOLDER -> onFolderRequest.invoke()
                                        HashSource.TEXT -> onTextRequest.invoke()
                                    }

                                    showSourceSelector = false
                                }
                            }

                            HashCheckerXSpacer64H()
                        }
                    }
                }

                HashCheckerXSpacer16W()

                HashCheckerXButton(stringResource(R.string.hash_generator_action)) {
                    showActionSelector = true
                }

                if (showActionSelector) {
                    HashCheckerXBottomSheet({ showActionSelector = false }) {
                        Column {
                            HashAction.entries.map {
                                HashCheckerXListItem(
                                    it.translatedName(),
                                    icon = when (it) {
                                        HashAction.GENERATE -> Icons.Rounded.Autorenew
                                        HashAction.COMPARE -> Icons.Rounded.Compare
                                    },
                                    iconDescription = "${stringResource(R.string.description_hash_action)} = ${it.translatedName()}"
                                ) {
                                    showActionSelector = false

                                    when (it) {
                                        HashAction.GENERATE -> {
                                            when (state.hashSource) {
                                                HashSource.FILE -> {
                                                    if (selectedFile == null) {
                                                        showHashCheckerXToast(
                                                            context,
                                                            context.getString(R.string.hash_generator_no_file)
                                                        )
                                                    } else {
                                                        viewModel.generateHashFromFile(
                                                            context,
                                                            selectedFile,
                                                        )
                                                    }
                                                }

                                                HashSource.FOLDER -> {
                                                    if (selectedFolder == null) {
                                                        showHashCheckerXToast(
                                                            context,
                                                            context.getString(R.string.hash_generator_no_folder)
                                                        )
                                                    } else {
                                                        viewModel.generateHashFromFolder(
                                                            context,
                                                            selectedFolder,
                                                        )
                                                    }
                                                }

                                                HashSource.TEXT -> {
                                                    if (selectedText == null) {
                                                        showHashCheckerXToast(
                                                            context,
                                                            context.getString(R.string.hash_generator_no_folder)
                                                        )
                                                    } else {
                                                        viewModel.generateHashFromText(
                                                            context,
                                                            selectedText,
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        HashAction.COMPARE -> viewModel.compareHashes()
                                    }
                                }
                            }

                            HashCheckerXSpacer64H()
                        }
                    }
                }
            }

            HashCheckerXSpacer32H()

            val hint = when {
                selectedFile != null -> selectedFile.path
                selectedFolder != null -> selectedFolder.path
                selectedText != null -> selectedText
                else -> stringResource(R.string.hash_generator_default_hint)
            }

            HashCheckerXHint(hint ?: stringResource(R.string.hash_generator_default_hint))
        }
    }

}

@Composable
private fun HashType.translatedName(): String {
    return when (this) {
        HashType.MD5 -> stringResource(R.string.md5)
        HashType.SHA_1 -> stringResource(R.string.sha1)
        HashType.SHA_224 -> stringResource(R.string.sha224)
        HashType.SHA_256 -> stringResource(R.string.sha256)
        HashType.SHA_384 -> stringResource(R.string.sha384)
        HashType.SHA_512 -> stringResource(R.string.sha512)
        HashType.SHA3_224 -> stringResource(R.string.sha3224)
        HashType.SHA3_256 -> stringResource(R.string.sha3256)
        HashType.SHA3_384 -> stringResource(R.string.sha3384)
        HashType.SHA3_512 -> stringResource(R.string.sha3512)
    }
}

@Composable
private fun HashSource.translatedName(): String {
    return when (this) {
        HashSource.FILE -> stringResource(R.string.hash_generator_from_file)
        HashSource.FOLDER -> stringResource(R.string.hash_generator_from_folder)
        HashSource.TEXT -> stringResource(R.string.hash_generator_from_text)
    }
}

@Composable
private fun HashAction.translatedName(): String {
    return when (this) {
        HashAction.GENERATE -> stringResource(R.string.hash_generator_action_generate)
        HashAction.COMPARE -> stringResource(R.string.hash_generator_action_compare)
    }
}

enum class HashGeneratorViewCase {
    LOWER,
    UPPER,
}

@Preview(showBackground = true)
@Composable
fun PreviewHashGeneratorView() {
    HashCheckerXTheme {
        Scaffold { innerPadding ->
            HashGeneratorView(
                viewModel = HashGeneratorViewModel(
                    hashGenerator = JdkHashGenerator(
                        defaultHashType = HashType.MD5,
                    ),
                    hashComparator = JdkHashComparator(),
                    logger = OrhanObutLogger(),
                    defaultHashType = HashType.MD5,
                ),
                viewCase = HashGeneratorViewCase.LOWER,
                innerPadding = innerPadding,
                onFileRequest = {},
                onFolderRequest = {},
                onTextRequest = {},
                selectedFile = null,
                selectedFolder = null,
                selectedText = null,
                onDone = {},
                onError = {},
                onCopy = {},
            )
        }
    }
}
