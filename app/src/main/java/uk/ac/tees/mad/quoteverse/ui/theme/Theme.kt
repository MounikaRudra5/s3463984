package uk.ac.tees.mad.quoteverse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = secondaryDark,
    tertiary = grayLight,
    surface = surfaceDark,
    surfaceContainer = cardBoardDark,
    onPrimary = surfaceVar4,
    onSecondary = surfaceVar5,
    onTertiary = surfaceVar6,
    surfaceVariant = comaDark
)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    secondary = secondaryLight,
    tertiary = grayDark,
    surface = surfaceLight,
    surfaceContainer = cardBoardLight,
    onPrimary = surfaceVar1,
    onSecondary = surfaceVar2,
    onTertiary = surfaceVar3,
    surfaceVariant = comaLight
)

@Composable
fun QuoteVerseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!darkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }


    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}