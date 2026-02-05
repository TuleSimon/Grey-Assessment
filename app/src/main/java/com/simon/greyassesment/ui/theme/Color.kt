package com.simon.greyassesment.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Text Colors
val textDefault = Color(0xFF1B1F28)
val textSoft = Color(0xFF49546C)

// Icon Colors
val iconSoft = Color(0xFF8592AD)
val iconDefault = Color(0xFF2D3443)
val iconInverse = Color(0xFFFFFFFF)

// Grey Colors
val LightGrey400 = Color(0xFFE1E4EB)

// Purple Colors
val purple100 = Color(0xFFEFE4FB)
val purple500 = Color(0xFF5E19B3)
val purple600 = Color(0xFF2F0D59)

// Button Colors
val buttonContainerColor = Color(0xFF8636E8)
val buttonPrimaryText = Color(0xFFFFFFFF)

// Home Colors
val homeBorderColor = Color(0xFFCFD3FB)
val homeIconContainer = Color(0x1f2467e3)
val homeIconTint = Color(0xFF082F74)

@Immutable
data class GreyColors(
    // Text
    val textDefault: Color = Color(0xFF1B1F28),
    val textSoft: Color = Color(0xFF49546C),

    // Icons
    val iconDefault: Color = Color(0xFF2D3443),
    val iconSoft: Color = Color(0xFF8592AD),
    val iconInverse: Color = Color(0xFFFFFFFF),

    // Surfaces
    val surface: Color = Color(0xFFFFFFFF),
    val surfaceVariant: Color = Color(0xFFE1E4EB),
    val background: Color = Color(0xFFFFFFFF),

    // Purple Palette
    val purple100: Color = Color(0xFFEFE4FB),
    val purple500: Color = Color(0xFF5E19B3),
    val purple600: Color = Color(0xFF2F0D59),

    // Button
    val buttonContainer: Color = Color(0xFF8636E8),
    val buttonText: Color = Color(0xFFFFFFFF),

    // Borders
    val border: Color = Color(0xFFE1E4EB),
    val borderAccent: Color = Color(0xFFCFD3FB),

    // Home specific
    val homeIconContainer: Color = Color(0x1f2467e3),
    val homeIconTint: Color = Color(0xFF082F74)
)

val LocalGreyColors = staticCompositionLocalOf { GreyColors() }