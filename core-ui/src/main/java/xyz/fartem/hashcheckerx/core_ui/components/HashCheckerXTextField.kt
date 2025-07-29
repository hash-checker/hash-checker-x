package xyz.fartem.hashcheckerx.core_ui.components

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp.minus(144.dp)

    if (text.isEmpty()) {
        return OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.width(width),
            singleLine = true,
        )
    }

    return Row {
        IconButton(
            onClick = {
                onClear.invoke()
            },
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterVertically),
        ) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = stringResource(R.string.clear),
            )
        }

        HashCheckerXSpacer16W()

        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.width(width),
            singleLine = true,
        )

        HashCheckerXSpacer16W()

        IconButton(
            onClick = {
                onCopy.invoke()
            },
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterVertically),
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.copy),
            )
        }
    }
}
