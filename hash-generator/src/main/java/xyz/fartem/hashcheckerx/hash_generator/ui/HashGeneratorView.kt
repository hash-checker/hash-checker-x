package xyz.fartem.hashcheckerx.hash_generator.ui

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXBottomSheet
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXButton
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXHint
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXListItem
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXProgressIndicator
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXSpacer16W
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXSpacer32H
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXSpacer4H
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXSpacer64H
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXSpacer8H
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXSurface
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXTextButton
import xyz.fartem.hashcheckerx.core_ui.components.HashCheckerXTextField
import xyz.fartem.hashcheckerx.core_ui.components.showHashCheckerXToast
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.hash_generator.R
import xyz.fartem.hashcheckerx.hash_generator.api.HashComparator
import xyz.fartem.hashcheckerx.hash_generator.api.HashGenerator
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashComparator
import xyz.fartem.hashcheckerx.hash_generator.impl.jdk.JdkHashGenerator
import xyz.fartem.hashcheckerx.hash_generator.model.HashAction
import xyz.fartem.hashcheckerx.hash_generator.model.HashSource
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HashGeneratorView(
    hashGenerator: HashGenerator,
    hashComparator: HashComparator,
    defaultHashType: HashType,
    innerPadding: PaddingValues,
    hashGeneratorViewCase: HashGeneratorViewCase,
    onFileRequest: () -> Unit,
    onFolderRequest: () -> Unit,
    onTextRequest: () -> Unit,
    selectedFile: Uri?,
    selectedFolder: Uri?,
    selectedText: String?,
    onDone: @Composable () -> Unit,
    onError: @Composable () -> Unit,
) {
    var hashType by remember { mutableStateOf(defaultHashType) }

    var customHash by remember { mutableStateOf("") }
    var generatedHash by remember { mutableStateOf("") }

    var hashSource by remember { mutableStateOf(HashSource.FILE) }

    var showTypeSelector by remember { mutableStateOf(false) }
    var showSourceSelector by remember { mutableStateOf(false) }
    var showActionSelector by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val generatorScope = rememberCoroutineScope()
    var showGeneratorProgress by remember { mutableStateOf(false) }

    HashCheckerXSurface(innerPadding) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HashCheckerXTextButton(hashType.translatedName()) {
                showTypeSelector = true
            }

            if (showTypeSelector) {
                ModalBottomSheet(
                    sheetState = rememberModalBottomSheetState(),
                    onDismissRequest = { showTypeSelector = false }
                ) {
                    Column {
                        HashType.entries.map {
                            HashCheckerXListItem(it.translatedName()) {
                                showTypeSelector = false
                                hashType = it

                                hashGenerator.setHashType(it)
                            }
                        }

                        HashCheckerXSpacer64H()
                    }
                }
            }

            if (showGeneratorProgress) {
                HashCheckerXProgressIndicator()
            }

            HashCheckerXSpacer8H()

            HashCheckerXTextField(
                value = when (hashGeneratorViewCase) {
                    HashGeneratorViewCase.UPPER -> customHash.uppercase()
                    else -> customHash
                },
                label = stringResource(R.string.hash_generator_custom_hash),
            ) { customHash = it }

            HashCheckerXSpacer4H()

            HashCheckerXTextField(
                value = when (hashGeneratorViewCase) {
                    HashGeneratorViewCase.UPPER -> generatedHash.uppercase()
                    else -> generatedHash
                },
                label = stringResource(R.string.hash_generator_generator_hash),
            ) { generatedHash = it }

            HashCheckerXSpacer32H()

            Row {
                HashCheckerXButton(stringResource(R.string.hash_generator_from)) {
                    showSourceSelector = true
                }

                if (showSourceSelector) {
                    HashCheckerXBottomSheet({ showSourceSelector = false }) {
                        Column {
                            HashSource.entries.map {
                                HashCheckerXListItem(it.translatedName()) {
                                    hashSource = it

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
                    fun runGenerator(generate: () -> Unit) {
                        generatorScope.launch {
                            showGeneratorProgress = true

                            withContext(Dispatchers.IO) {
                                generate.invoke()
                            }

                            showGeneratorProgress = false
                        }
                    }

                    HashCheckerXBottomSheet({ showActionSelector = false }) {
                        Column {
                            HashAction.entries.map {
                                HashCheckerXListItem(it.translatedName()) {
                                    showActionSelector = false

                                    when (it) {
                                        HashAction.GENERATE -> {
                                            when (hashSource) {
                                                HashSource.FILE -> {
                                                    if (selectedFile != null) {
                                                        runGenerator {
                                                            val hash = hashGenerator.fromFile(context, selectedFile)

                                                            if (hash != null) {
                                                                generatedHash = hash
                                                            } else {
                                                                showHashCheckerXToast(
                                                                    context,
                                                                    context.getString(R.string.hash_generator_error)
                                                                )
                                                            }
                                                        }
                                                    } else {
                                                        showHashCheckerXToast(
                                                            context,
                                                            stringResource(R.string.hash_generator_no_file)
                                                        )

                                                        onError.invoke()
                                                    }
                                                }

                                                HashSource.FOLDER -> {
                                                    if (selectedFolder != null) {
                                                        runGenerator {
                                                            val hash = hashGenerator.fromFolder(context, selectedFolder)

                                                            if (hash != null) {
                                                                generatedHash = hash
                                                            } else {
                                                                showHashCheckerXToast(
                                                                    context,
                                                                    context.getString(R.string.hash_generator_error)
                                                                )
                                                            }
                                                        }
                                                    } else {
                                                        showHashCheckerXToast(
                                                            context,
                                                            stringResource(R.string.hash_generator_no_folder)
                                                        )

                                                        onError.invoke()
                                                    }
                                                }

                                                HashSource.TEXT -> {
                                                    if (!selectedText.isNullOrEmpty()) {
                                                        runGenerator {
                                                            val hash = hashGenerator.fromText(selectedText)

                                                            if (hash != null) {
                                                                generatedHash = hash
                                                            } else {
                                                                showHashCheckerXToast(
                                                                    context,
                                                                    context.getString(R.string.hash_generator_error)
                                                                )
                                                            }
                                                        }
                                                    } else {
                                                        showHashCheckerXToast(
                                                            context,
                                                            context.getString(R.string.hash_generator_empty_text)
                                                        )

                                                        onError.invoke()
                                                    }
                                                }
                                            }
                                        }

                                        HashAction.COMPARE -> {
                                            when {
                                                customHash.isEmpty() -> {
                                                    showHashCheckerXToast(
                                                        context,
                                                        context.getString(R.string.hash_generator_custom_hash_empty),
                                                    )

                                                    onError.invoke()
                                                }

                                                generatedHash.isEmpty() -> {
                                                    showHashCheckerXToast(
                                                        context,
                                                        context.getString(R.string.hash_generator_generated_hash_empty),
                                                    )

                                                    onError.invoke()
                                                }

                                                hashComparator.compare(customHash, generatedHash) -> {
                                                    showHashCheckerXToast(
                                                        context,
                                                        context.getString(R.string.hash_generator_equals),
                                                    )

                                                    onDone.invoke()
                                                }

                                                else -> {
                                                    showHashCheckerXToast(
                                                        context,
                                                        context.getString(R.string.hash_generator_different),
                                                    )

                                                    onDone.invoke()
                                                }
                                            }
                                        }
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

            HashCheckerXHint(hint!!)
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
                hashGenerator = JdkHashGenerator(),
                hashComparator = JdkHashComparator(),
                defaultHashType = HashType.MD5,
                innerPadding = innerPadding,
                hashGeneratorViewCase = HashGeneratorViewCase.LOWER,
                onFileRequest = {},
                onFolderRequest = {},
                onTextRequest = {},
                selectedFile = null,
                selectedFolder = null,
                selectedText = null,
                onDone = {},
                onError = {},
            )
        }
    }
}
