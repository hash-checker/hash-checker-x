package xyz.fartem.hashcheckerx.core_ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HashCheckerXBottomSheet(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        onDismissRequest = onDismissRequest,
    ) { content.invoke() }
}