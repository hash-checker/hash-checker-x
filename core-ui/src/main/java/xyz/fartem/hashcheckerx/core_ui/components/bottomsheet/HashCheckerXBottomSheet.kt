package xyz.fartem.hashcheckerx.core_ui.components.bottomsheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismissRequest,
    ) { content.invoke() }
}
