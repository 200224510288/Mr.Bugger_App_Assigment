package com.example.mrbugger_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import com.example.mrbugger_app.R
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme

// Light and Dark theme colors
private val DarkColorPalette = darkColorScheme(
    primary = PrimaryYellowLight,
    secondary = SecondaryColor,
    background = Darkgray,
    surface = PrimaryYellowDark,
    onPrimary = BackgroundColor,
    onSecondary = BackgroundColor,
    onBackground = BackgroundColor,
    onSurface = BackgroundColor
)

private val LightColorPalette = lightColorScheme(
    primary = PrimaryYellowLight,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = TextfeildColor,
    onPrimary = TextColor,
    onSecondary = BackgroundColor,
    onBackground = TextColor,
    onSurface = TextColor
)

@Composable
fun MrBurgerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
val Poppins = FontFamily(
    Font(R.font.poppinslight, FontWeight.Light),
    Font(R.font.poppinsregular, FontWeight.Normal),
    Font(R.font.poppinsmedium, FontWeight.Medium),
    Font(R.font.poppinssemibold, FontWeight.SemiBold),
    Font(R.font.poppinsbold, FontWeight.Bold),
    Font(R.font.poppinsextrabold, FontWeight.ExtraBold)
)
