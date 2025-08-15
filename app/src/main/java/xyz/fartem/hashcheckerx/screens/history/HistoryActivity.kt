package xyz.fartem.hashcheckerx.screens.history

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.stringResource
import xyz.fartem.hashcheckerx.R
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.history.ui.HistoryView
import xyz.fartem.hashcheckerx.history.wrapper.HistoryWrapper
import xyz.fartem.hashcheckerx.history.wrapper.HistoryWrapperView
import javax.inject.Inject

class HistoryActivity : ComponentActivity() {
    @Inject
    lateinit var historyWrapper: HistoryWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HashCheckerXTheme {
                Scaffold(
                    topBar = {
                        HashCheckerXTopBar(
                            title = stringResource(R.string.settings),
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                        contentDescription = "Localized description",
                                    )
                                }
                            },
                        )
                    },
                ) { paddingValues ->
                    HistoryWrapperView(historyWrapper) {
                        HistoryView(
                            innerPadding = paddingValues,
                            hashOutputs = emptyList(),
                        )
                    }
                }
            }
        }
    }
}
