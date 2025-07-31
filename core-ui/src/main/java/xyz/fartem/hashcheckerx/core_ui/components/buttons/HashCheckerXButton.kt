package xyz.fartem.hashcheckerx.core_ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HashCheckerXButton(
    title: String,
    onClick: () -> Unit,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = Modifier.size(
            width = 120.dp,
            height = 52.dp
        ),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            disabledContentColor = MaterialTheme.colorScheme.surface,
        ),
    ) { Text(title, fontSize = 18.sp) }
}
