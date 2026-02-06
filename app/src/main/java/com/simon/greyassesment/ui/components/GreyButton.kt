package com.simon.greyassesment.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import com.simon.greyassesment.ui.theme.greyColors
import com.simon.greyassesment.ui.theme.greyShapes

@Composable
fun GreyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    iconContentDescription: String? = null,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        shape = MaterialTheme.greyShapes.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.greyColors.buttonContainer,
            contentColor = MaterialTheme.greyColors.buttonText
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    color = MaterialTheme.greyColors.buttonText,
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.greyColors.buttonText
            )
            if (icon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = icon,
                    contentDescription = iconContentDescription,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.greyColors.buttonText
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreyButtonPreview() {
    GreyAssesmentTheme {
        GreyButton(
            text = "View full path",
            onClick = {}
        )
    }
}