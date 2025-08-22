package xyz.fartem.hashcheckerx.core_ui.components.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer16W

@Composable
fun HashCheckerXListItem(
    icon: ImageVector,
    iconDescription: String,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            tint = MaterialTheme.colorScheme.primary,
        )

        HashCheckerXSpacer16W()

        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
