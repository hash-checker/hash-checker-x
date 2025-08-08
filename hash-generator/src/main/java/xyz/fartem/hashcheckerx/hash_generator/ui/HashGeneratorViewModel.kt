package xyz.fartem.hashcheckerx.hash_generator.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.fartem.hashcheckerx.core.logger.api.Logger
import xyz.fartem.hashcheckerx.hash_generator.api.HashComparator
import xyz.fartem.hashcheckerx.hash_generator.api.HashGenerator
import xyz.fartem.hashcheckerx.hash_generator.model.HashSource
import xyz.fartem.hashcheckerx.hash_generator.model.HashType

data class HashGeneratorViewModelState(
    val hashType: HashType = HashType.MD5,
    val hashSource: HashSource = HashSource.FILE,
    val customHash: String = "",
    val generatedHash: String = "",
    val isGenerating: Boolean = false,
)

sealed class HashGeneratorEvent {
    data object GenerationError : HashGeneratorEvent()
    data object ComparisonError : HashGeneratorEvent()

    data object GenerationFromFileError : HashGeneratorEvent()
    data object GenerationFromFolderError : HashGeneratorEvent()
    data object GenerationFromTextError : HashGeneratorEvent()

    data object CustomHashEmptyError : HashGeneratorEvent()
    data object GeneratedHashEmptyError : HashGeneratorEvent()

    data class Comparison(val result: Boolean) : HashGeneratorEvent()
}

class HashGeneratorViewModel(
    private val hashGenerator: HashGenerator,
    private val hashComparator: HashComparator,
    private val logger: Logger,
    defaultHashType: HashType,
) : ViewModel() {
    private val _state = MutableStateFlow(
        HashGeneratorViewModelState(
            hashType = defaultHashType,
        ),
    )
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<HashGeneratorEvent>()
    val events: SharedFlow<HashGeneratorEvent> = _events.asSharedFlow()

    fun onHashTypeSelected(type: HashType) {
        _state.update {
            it.copy(hashType = type)
        }
    }

    fun onCustomHashChange(newHash: String) {
        _state.update {
            it.copy(customHash = newHash)
        }
    }

    fun onGeneratedHashChanged(newHash: String) {
        _state.update {
            it.copy(generatedHash = newHash)
        }
    }

    fun onHashSourceSelected(source: HashSource) {
        _state.update {
            it.copy(hashSource = source)
        }
    }

    fun generateHashFromFile(context: Context, selectedFile: Uri) {
        viewModelScope.launch {
            try {
                handleGenerate(context, selectedFile = selectedFile)
            } catch (e: Exception) {
                logger.error(e.stackTraceToString())

                _events.emit(HashGeneratorEvent.GenerationError)
            }
        }
    }

    fun generateHashFromFolder(context: Context, selectedFolder: Uri) {
        viewModelScope.launch {
            try {
                handleGenerate(context, selectedFolder = selectedFolder)
            } catch (e: Exception) {
                logger.error(e.stackTraceToString())

                _events.emit(HashGeneratorEvent.GenerationError)
            }
        }
    }

    fun generateHashFromText(context: Context, selectedText: String) {
        viewModelScope.launch {
            try {
                handleGenerate(context, selectedText = selectedText)
            } catch (e: Exception) {
                logger.error(e.stackTraceToString())

                _events.emit(HashGeneratorEvent.GenerationError)
            }
        }
    }

    private suspend fun handleGenerate(
        context: Context,
        selectedFile: Uri? = null,
        selectedFolder: Uri? = null,
        selectedText: String? = null,
    ) {
        val hashType = _state.value.hashType
        val hashSource = _state.value.hashSource

        val hash: String? = withContext(Dispatchers.IO) {
            when (hashSource) {
                HashSource.FILE -> {
                    if (selectedFile != null) {
                        runGeneration {
                            hashGenerator.fromFile(hashType, context, selectedFile)
                        }
                    } else {
                        null
                    }
                }

                HashSource.FOLDER -> {
                    if (selectedFolder != null) {
                        runGeneration {
                            hashGenerator.fromFolder(hashType, context, selectedFolder)
                        }
                    } else {
                        null
                    }
                }

                HashSource.TEXT -> {
                    if (!selectedText.isNullOrEmpty()) {
                        runGeneration {
                            hashGenerator.fromText(hashType, selectedText)
                        }
                    } else {
                        null
                    }
                }
            }
        }

        if (hash != null) {
            _state.update {
                it.copy(generatedHash = hash)
            }
        } else {
            when (hashSource) {
                HashSource.FILE -> _events.emit(HashGeneratorEvent.GenerationFromFileError)
                HashSource.FOLDER -> _events.emit(HashGeneratorEvent.GenerationFromFolderError)
                HashSource.TEXT -> _events.emit(HashGeneratorEvent.GenerationFromTextError)
            }
        }
    }

    private fun runGeneration(job: () -> String?): String? {
        try {
            _state.update {
                it.copy(isGenerating = true)
            }

            val result = job.invoke()

            _state.update {
                it.copy(isGenerating = false)
            }

            return result
        } catch (e: Exception) {
            e.message?.let { logger.error(it) }

            _state.update {
                it.copy(isGenerating = false)
            }

            return null
        }
    }

    fun compareHashes() {
        viewModelScope.launch {
            handleCompare()
        }
    }

    private suspend fun handleCompare() {
        val customHash = _state.value.customHash
        val generatedHash = _state.value.generatedHash

        when {
            customHash.isEmpty() -> {
                _events.emit(HashGeneratorEvent.CustomHashEmptyError)
            }

            generatedHash.isEmpty() -> {
                _events.emit(HashGeneratorEvent.GeneratedHashEmptyError)
            }

            hashComparator.compare(customHash, generatedHash) -> {
                _events.emit(HashGeneratorEvent.Comparison(true))
            }

            else -> {
                _events.emit(HashGeneratorEvent.Comparison(false))
            }
        }
    }
}

class HashGeneratorViewModelFactory(
    private val hashGenerator: HashGenerator,
    private val hashComparator: HashComparator,
    private val logger: Logger,
    private val defaultHashType: HashType,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HashGeneratorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HashGeneratorViewModel(
                hashGenerator = hashGenerator,
                hashComparator = hashComparator,
                logger = logger,
                defaultHashType = defaultHashType,
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
