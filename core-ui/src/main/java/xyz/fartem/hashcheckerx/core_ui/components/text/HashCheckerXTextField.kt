package xyz.fartem.hashcheckerx.core_ui.components.text

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.fartem.hashcheckerx.core_ui.R

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HashCheckerXTextField(
    text: String,
    label: String,
    onValueChange: (value: String) -> Unit,
    onClear: () -> Unit,
    onCopy: () -> Unit,
) {
    return OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = if (text.isNotEmpty()) {
            {
                IconButton(
                    onClick = {
                        onClear.invoke()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = stringResource(R.string.clear),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        } else null,
        trailingIcon = if (text.isNotEmpty()) {
            {
                IconButton(
                    onClick = {
                        onCopy.invoke()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ContentCopy,
                        contentDescription = stringResource(R.string.copy),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        } else null,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
    )
}
