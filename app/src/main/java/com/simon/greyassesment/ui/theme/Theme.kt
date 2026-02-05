package com.simon.greyassesment.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = buttonContainerColor,
    onPrimary = buttonPrimaryText,
    primaryContainer = purple100,
    onPrimaryContainer = purple600,
    secondary = purple500,
    onSecondary = iconInverse,
    secondaryContainer = purple100,
    onSecondaryContainer = purple600,
    tertiary = homeIconTint,
    onTertiary = iconInverse,
    tertiaryContainer = homeIconContainer,
    onTertiaryContainer = homeIconTint,
    background = Color.White,
    onBackground = textDefault,
    surface = Color.White,
    onSurface = textDefault,
    surfaceVariant = LightGrey400,
    onSurfaceVariant = textSoft,
    outline = LightGrey400,
    outlineVariant = homeBorderColor,
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B)
)

private val DarkColorScheme = darkColorScheme(
    primary = buttonContainerColor,
    onPrimary = buttonPrimaryText,
    primaryContainer = purple600,
    onPrimaryContainer = purple100,
    secondary = purple500,
    onSecondary = iconInverse,
    secondaryContainer = purple600,
    onSecondaryContainer = purple100,
    tertiary = homeIconTint,
    onTertiary = iconInverse,
    tertiaryContainer = homeIconContainer,
    onTertiaryContainer = homeIconTint,
    background = Color(0xFF1B1F28),
    onBackground = Color.White,
    surface = Color(0xFF1B1F28),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2D3443),
    onSurfaceVariant = LightGrey400,
    outline = Color(0xFF49546C),
    outlineVariant = Color(0xFF2D3443),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC)
)

private val LightGreyColors = GreyColors()

val MaterialTheme.greyColors: GreyColors
    @Composable
    @ReadOnlyComposable
    get() = LocalGreyColors.current

val MaterialTheme.greyTypography: GreyTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalGreyTypography.current

@Composable
fun GreyAssesmentTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val greyColors = LightGreyColors


    CompositionLocalProvider(
        LocalGreyColors provides greyColors,
        LocalGreyTypography provides GreyTypography()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}