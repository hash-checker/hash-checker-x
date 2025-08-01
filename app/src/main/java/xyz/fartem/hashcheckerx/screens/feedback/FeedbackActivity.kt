package xyz.fartem.hashcheckerx.screens.feedback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import xyz.fartem.hashcheckerx.BuildConfig
import xyz.fartem.hashcheckerx.R
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXTopBar
import xyz.fartem.hashcheckerx.core_ui.theme.HashCheckerXTheme
import xyz.fartem.hashcheckerx.feedback.model.Feedback
import xyz.fartem.hashcheckerx.feedback.sender.api.FeedbackSender
import xyz.fartem.hashcheckerx.feedback.ui.FeedbackView
import xyz.fartem.hashcheckerx.feedback.ui.FeedbackViewModel
import xyz.fartem.hashcheckerx.feedback.ui.FeedbackViewModelFactory
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class FeedbackActivity : ComponentActivity() {
    @Inject
    lateinit var feedbackSender: FeedbackSender

    @Inject
    lateinit var feedback: Feedback

    private val feedbackViewModel: FeedbackViewModel by viewModels {
        FeedbackViewModelFactory(
            feedbackSender = feedbackSender,
            feedback = feedback,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HashCheckerXTheme {
                Scaffold(
                    topBar = {
                        HashCheckerXTopBar(
                            title = stringResource(R.string.feedback),
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
                    FeedbackView(
                        viewModel = feedbackViewModel,
                        paddingValues = paddingValues,
                    )
                }
            }
        }
    }
}
