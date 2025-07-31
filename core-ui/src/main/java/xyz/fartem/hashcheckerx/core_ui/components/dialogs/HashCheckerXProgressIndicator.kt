package xyz.fartem.hashcheckerx.core_ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import xyz.fartem.hashcheckerx.core_ui.R
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer16H
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer32H

@Composable
fun HashCheckerXProgressIndicator() {
    Dialog(onDismissRequest = {}) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HashCheckerXSpacer16H()

                CircularProgressIndicator()

                HashCheckerXSpacer32H()

                Text(stringResource(R.string.text_progress_indicator))
            }
        }
    }
}
