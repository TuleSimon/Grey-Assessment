package com.simon.greyassesment.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.simon.greyassesment.R

// Aeonik Font Family
val AeonikFontFamily = FontFamily(
    Font(R.font.aeonik_air, FontWeight.W100),
    Font(R.font.aeonik_air_italic, FontWeight.W100, FontStyle.Italic),
    Font(R.font.aeonik_thin, FontWeight.Thin),
    Font(R.font.aeonik_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.aeonik_light, FontWeight.Light),
    Font(R.font.aeonik_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.aeonik_regular, FontWeight.Normal),
    Font(R.font.aeonik_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.aeonik_medium, FontWeight.Medium),
    Font(R.font.aeonik_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.aeonik_bold, FontWeight.Bold),
    Font(R.font.aeonik_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.aeonik_black, FontWeight.Black),
    Font(R.font.aeonik_black_italic, FontWeight.Black, FontStyle.Italic)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        color = textDefault
    ),
    displayMedium = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        color = textDefault
    ),
    displaySmall = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        color = textDefault
    ),
    headlineLarge = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        color = textDefault
    ),
    headlineMedium = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        color = textDefault
    ),
    headlineSmall = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = textDefault
    ),
    titleLarge = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = textDefault
    ),
    titleMedium = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = textDefault
    ),
    titleSmall = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = textDefault
    ),
    bodyLarge = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = textDefault
    ),
    bodyMedium = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = textDefault
    ),
    bodySmall = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = textSoft
    ),
    labelLarge = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = textDefault
    ),
    labelMedium = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = textDefault
    ),
    labelSmall = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = textSoft
    )
)

@Immutable
data class GreyTypography(
    val displayLarge: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        color = textDefault
    ),
    val displayMedium: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        color = textDefault
    ),
    val displaySmall: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        color = textDefault
    ),

    val headingLarge: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        color = textDefault
    ),
    val headingMedium: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        color = textDefault
    ),
    val headingSmall: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        color = textDefault
    ),

    val titleLarge: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        color = textDefault
    ),
    val titleMedium: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        color = textDefault
    ),
    val titleSmall: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = textDefault
    ),

    // Body styles
    val bodyLarge: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = textDefault
    ),
    val bodyMedium: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = textDefault
    ),
    val bodySmall: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = textSoft
    ),

    val labelLarge: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = textDefault
    ),
    val labelMedium: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = textSoft
    ),
    val labelSmall: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        color = textSoft
    ),

    val buttonLarge: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = buttonPrimaryText
    ),
    val buttonMedium: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = buttonPrimaryText
    ),
    val buttonSmall: TextStyle = TextStyle(
        fontFamily = AeonikFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = buttonPrimaryText
    )
)

val LocalGreyTypography = staticCompositionLocalOf { GreyTypography() }