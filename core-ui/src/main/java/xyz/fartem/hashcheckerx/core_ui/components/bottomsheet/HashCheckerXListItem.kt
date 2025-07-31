package xyz.fartem.hashcheckerx.core_ui.components.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer16W
import xyz.fartem.hashcheckerx.core_ui.components.common.HashCheckerXSpacer40W

@Composable
fun HashCheckerXListItem(
    text: String,
    icon: ImageVector?,
    iconDescription: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(16.dp),
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
                tint = MaterialTheme.colorScheme.primary,
            )

            HashCheckerXSpacer16W()
        } else {
            HashCheckerXSpacer40W()
        }

        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
