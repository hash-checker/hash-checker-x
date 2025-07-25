package xyz.fartem.hashcheckerx.core_ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HashCheckerXHint(text: String) {
    OutlinedTextField(
        value = text,
        enabled = false,
        onValueChange = {},
        textStyle = TextStyle(
            textAlign = TextAlign.Center
        ),
        readOnly = true,
    )
}
