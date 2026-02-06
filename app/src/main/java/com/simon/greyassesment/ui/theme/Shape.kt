package com.simon.greyassesment.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Immutable
data class GreyShapes(
    val none: RoundedCornerShape = RoundedCornerShape(0.dp),
    val extraSmall: RoundedCornerShape = RoundedCornerShape(4.dp),
    val small: RoundedCornerShape = RoundedCornerShape(8.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(12.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(24.dp),
    val full: RoundedCornerShape = RoundedCornerShape(50),

    // Specific component shapes
    val button: RoundedCornerShape = RoundedCornerShape(8.dp),
    val card: RoundedCornerShape = RoundedCornerShape(8.dp),
    val cardMedium: RoundedCornerShape = RoundedCornerShape(12.dp),
    val bottomSheet: RoundedCornerShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    val dialog: RoundedCornerShape = RoundedCornerShape(16.dp),
    val textField: RoundedCornerShape = RoundedCornerShape(8.dp),
    val chip: RoundedCornerShape = RoundedCornerShape(50),
    val badge: RoundedCornerShape = RoundedCornerShape(50)
)

val LocalGreyShapes = staticCompositionLocalOf { GreyShapes() }