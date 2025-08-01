package xyz.fartem.hashcheckerx.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = ThemeDark.ColorPrimary,
    primaryContainer = ThemeDark.ColorPrimary,
    onPrimary = ThemeDark.ColorOnPrimary,
    secondary = ThemeDark.ColorPrimary,
    secondaryContainer = ThemeDark.ColorPrimary,
    background = ThemeDark.ColorBackground,
    surface = ThemeDark.ColorDisabled2,
    surfaceContainer = ThemeDark.ColorDisabled,
)

private val LightColorScheme = lightColorScheme(
    primary = ThemeLight.ColorPrimary,
    primaryContainer = ThemeLight.ColorPrimary,
    onPrimary = ThemeLight.ColorOnPrimary,
    secondary = ThemeLight.ColorPrimary,
    secondaryContainer = ThemeLight.ColorPrimary,
    background = ThemeLight.ColorBackground,
    surface = ThemeLight.ColorDisabled2,
    surfaceContainer = ThemeLight.ColorDisabled,
)

@Composable
fun HashCheckerXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
