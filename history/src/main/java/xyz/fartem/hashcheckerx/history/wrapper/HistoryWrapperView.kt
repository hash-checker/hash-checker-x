package xyz.fartem.hashcheckerx.history.wrapper

import androidx.compose.runtime.Composable

@Composable
fun HistoryWrapperView(
    historyWrapper: HistoryWrapper,
    ui: @Composable (HistoryWrapper) -> Unit,
) {
    ui.invoke(historyWrapper)
}
