package xyz.fartem.hashcheckerx.history.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXSurface
import xyz.fartem.hashcheckerx.hash_generator.model.HashOutput

@Composable
fun HistoryView(
    innerPadding: PaddingValues,
    hashOutputs: List<HashOutput>,
) {
    HashCheckerXSurface(innerPadding) {

    }
}
