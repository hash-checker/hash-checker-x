package xyz.fartem.hashcheckerx.feedback.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.fartem.hashcheckerx.core_ui.components.buttons.HashCheckerXButton
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer16H
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer8H
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXSurface
import xyz.fartem.hashcheckerx.core_ui.components.text.HashCheckerXInfoText
import xyz.fartem.hashcheckerx.core_ui.components.text.HashCheckerXTextField
import xyz.fartem.hashcheckerx.feedback.R

@Composable
fun FeedbackView(
    viewModel: FeedbackViewModel,
    paddingValues: PaddingValues,
) {
    HashCheckerXSurface(innerPadding = paddingValues) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val state = viewModel.state.collectAsStateWithLifecycle().value
                val feedback = state.feedback

                Icon(
                    imageVector = Icons.Rounded.Email,
                    contentDescription = stringResource(R.string.description_email_icon),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(256.dp),
                )

                HashCheckerXTextField(
                    text = state.feedback.message,
                    label = stringResource(R.string.label_feedback),
                    onValueChange = { viewModel.updateFeedbackMessage(it) },
                    minLines = 3,
                    maxLines = 10,
                )

                HashCheckerXSpacer16H()

                HashCheckerXInfoText(
                    title = stringResource(R.string.title_app_version),
                    value = "${feedback.appVersionName} (${feedback.appVersionCode})"
                )
                HashCheckerXInfoText(
                    title = stringResource(R.string.title_android),
                    value = feedback.osVersion,
                )
                HashCheckerXInfoText(
                    title = stringResource(R.string.title_manufacturer),
                    value = feedback.manufacturer,
                )
                HashCheckerXInfoText(
                    title = stringResource(R.string.title_model),
                    value = feedback.model,
                )
            }

            Column {
                HashCheckerXButton(
                    stringResource(R.string.action_send),
                    expand = true,
                ) {
                    viewModel.sendFeedback()
                }

                HashCheckerXSpacer8H()
            }
        }
    }
}
