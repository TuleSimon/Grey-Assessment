package com.simon.greyassesment.features.learnpath.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simon.greyassesment.R
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.ui.components.GreyButton
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import com.simon.greyassesment.ui.theme.greyColors
import com.simon.greyassesment.ui.theme.greyShapes
import com.simon.greyassesment.ui.theme.greySpacing
import com.simon.greyassesment.ui.theme.greyTypography
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeAchievementBottomSheet(
    achievement: Badge,
    onDismiss: () -> Unit,
    onShare: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        BadgeAchievementContent(
            achievement = achievement,
            onShare = onShare
        )
    }
}

@Composable
fun BadgeAchievementContent(
    achievement: Badge,
    onShare: () -> Unit
) {
    var isFlipped by remember { mutableStateOf(false) }

    // Badge entrance animation
    val badgeScale = remember { Animatable(0f) }
    val badgeAlpha = remember { Animatable(0f) }

    // Flip animation
    val flipRotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "flipRotation"
    )

    // Rotating light rays animation
    val infiniteTransition = rememberInfiniteTransition(label = "lightRays")
    val raysRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "raysRotation"
    )

    // Pulse animation for rays
    val raysPulse by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "raysPulse"
    )

    LaunchedEffect(Unit) {
        // Animate badge entrance
        badgeAlpha.animateTo(1f, animationSpec = tween(300))
        badgeScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.greyColors.border,
                    shape = MaterialTheme.greyShapes.full
                )
                .clickable { isFlipped = !isFlipped }
                .padding(
                    horizontal = MaterialTheme.greySpacing.spacing14,
                    vertical = MaterialTheme.greySpacing.spacing11
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.greySpacing.spacing6)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = "Flip",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.greyColors.textDefault
            )
            Text(
                text = stringResource(R.string.flip_badge),
                style = MaterialTheme.greyTypography.bodySmall.copy(
                    color = MaterialTheme.greyColors.textDefault,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(250.dp)
        ) {

            Canvas(
                modifier = Modifier
                    .size(250.dp)
                    .graphicsLayer {
                        rotationZ = raysRotation
                        alpha = badgeAlpha.value * raysPulse
                    }
            ) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val rayCount = 9
                val innerRadius = size.minDimension * 0.2f
                val outerRadius = size.minDimension * 0.55f

                for (i in 0 until rayCount) {
                    val angle = (i * 360f / rayCount) * (PI / 180f).toFloat()
                    val nextAngle = ((i + 0.5f) * 360f / rayCount) * (PI / 180f).toFloat()


                    val rayColor = if (i % 2 == 0) {
                        Color(0xFFE1E4EB).copy(alpha = 0.6f)
                    } else {
                        Color(0x038636e8).copy(alpha = 0.1f)
                    }

                    val path = Path().apply {
                        // Start from inner point
                        moveTo(
                            center.x + innerRadius * cos(angle),
                            center.y + innerRadius * sin(angle)
                        )
                        // Go to outer edge
                        lineTo(
                            center.x + outerRadius * cos(angle),
                            center.y + outerRadius * sin(angle)
                        )
                        // Arc to next point (simplified as line for triangle ray)
                        lineTo(
                            center.x + outerRadius * cos(nextAngle),
                            center.y + outerRadius * sin(nextAngle)
                        )
                        // Back to inner
                        lineTo(
                            center.x + innerRadius * cos(nextAngle),
                            center.y + innerRadius * sin(nextAngle)
                        )
                        close()
                    }

                    drawPath(
                        path = path,
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White,
                                rayColor
                            ),
                            center = center,
                            radius = outerRadius
                        )
                    )
                }
            }


            Image(
                painter = painterResource(id = achievement.icon),
                contentDescription = achievement.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(240.dp)
                    .wrapContentHeight()
                    .graphicsLayer {
                        scaleX = badgeScale.value
                        scaleY = badgeScale.value
                        alpha = badgeAlpha.value
                        rotationY = flipRotation
                        cameraDistance = 12f * density
                    }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "${achievement.name} earned",
            style = MaterialTheme.greyTypography.headingMedium.copy(
                color = MaterialTheme.greyColors.textDefault,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 28.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))


        Text(
            text = achievement.description,
            style = MaterialTheme.greyTypography.bodyMedium.copy(
                color = MaterialTheme.greyColors.textSoft,
                fontSize = 14.sp,
                lineHeight = 20.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.68f)
        )

        Spacer(modifier = Modifier.height(40.dp))

        GreyButton("Share your achievement") {
            onShare()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BadgeAchievementContentPreview() {
    GreyAssesmentTheme {
        BadgeAchievementContent(
            achievement = Badge(
                id = "badge_git",
                name = "Git Guru",
                icon = R.drawable.badge_completed,
                description = "Versioned & valiant. You don't just write code. You commit to it.",
                earnedAt = System.currentTimeMillis()
            ),

            onShare = {}
        )
    }
}