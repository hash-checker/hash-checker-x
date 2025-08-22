package xyz.fartem.hashcheckerx.history.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.InsertDriveFile
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.TextFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import xyz.fartem.hashcheckerx.core_ui.components.lists.HashCheckerXListItem
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXSurface
import xyz.fartem.hashcheckerx.hash_generator.model.HashOutput
import xyz.fartem.hashcheckerx.hash_generator.model.HashSource
import xyz.fartem.hashcheckerx.history.R

@Composable
fun HistoryView(
    innerPadding: PaddingValues,
    hashOutputs: List<HashOutput>,
    onCopy: (HashOutput) -> Unit,
) {
    HashCheckerXSurface(innerPadding) {
        Column {
            hashOutputs.map {
                HashCheckerXListItem(
                    icon = when (it.hashSource) {
                        HashSource.FILE -> Icons.AutoMirrored.Rounded.InsertDriveFile
                        HashSource.FOLDER -> Icons.Rounded.Folder
                        HashSource.TEXT -> Icons.Rounded.TextFormat
                    },
                    iconDescription = stringResource(
                        R.string.description_history_item,
                        it.hashSource
                    ),
                    title = it.hashValue,
                    onClick = { onCopy.invoke(it) },
                )
            }
        }
    }
}
