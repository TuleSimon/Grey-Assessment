package com.simon.greyassesment.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

object GreySpacing {
    val spacing16 = 16.dp
    val spacing25 = 16.dp
    val spacing7 = 8.dp
    val spacing4 = 4.dp
}

val LocalGreySpacing = staticCompositionLocalOf { GreySpacing }