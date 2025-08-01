package xyz.fartem.hashcheckerx.feedback.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.fartem.hashcheckerx.core_ui.components.buttons.HashCheckerXButton
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer16H
import xyz.fartem.hashcheckerx.core_ui.components.screens.HashCheckerXSurface
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
            Column {
                val state = viewModel.state.collectAsStateWithLifecycle().value
                val feedback = state.feedback

//        Icon(
//            painter = painterResource(id = R.drawable.ic_email),
//            contentDescription = null,
//            modifier = Modifier.size(48.dp)
//        )

                HashCheckerXSpacer16H()

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Feedback") },
                    placeholder = { Text("Feedback") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 10,
                    singleLine = false
                )

                HashCheckerXSpacer16H()

                FeedbackInfoItem(
                    title = stringResource(R.string.title_app_version),
                    value = "${feedback.appVersionName} (${feedback.appVersionCode})"
                )
                FeedbackInfoItem(
                    title = stringResource(R.string.title_android),
                    value = feedback.osVersion,
                )
                FeedbackInfoItem(
                    title = stringResource(R.string.title_manufacturer),
                    value = feedback.manufacturer,
                )
                FeedbackInfoItem(
                    title = stringResource(R.string.title_model),
                    value = feedback.model,
                )
            }

            HashCheckerXButton(
                stringResource(R.string.action_send),
                expand = true,
            ) {
                viewModel.sendFeedback()
            }
        }
    }
}

@Composable
private fun FeedbackInfoItem(title: String, value: String) {
    val colorCommonText = MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = colorCommonText
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = colorCommonText
        )
    }
}
