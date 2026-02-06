package com.simon.greyassesment.features.learnpath.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.simon.greyassesment.R
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.features.home.presentation.components.share
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import com.simon.greyassesment.ui.theme.greyColors
import com.simon.greyassesment.ui.theme.greyTypography
import kotlinx.coroutines.delay


sealed interface BadgeState {
    object COMPLETED : BadgeState
    data class InProgress(val progress: Float) : BadgeState
    object LOCKED : BadgeState
}

data class LevelNode(
    val id: Int,
    val title: String,
    val state: BadgeState,
    val icon: Int,
    val subTitle: String? = null,
    val badge: Badge? = null
)

object LearningPathConfig {
    val COMPLETED_ICON_SIZE = 100.dp
    val INCOMPLETE_ICON_SIZE = 105.dp
    val VERTICAL_SPACING = 180.dp
    val STROKE_WIDTH = 1.5.dp
    val MIN_HORIZONTAL_PADDING = 16.dp
    val TEXT_CONTAINER_WIDTH = 130.dp
}

@Composable
fun PathsCustomComponentBySimon(levels: List<LevelNode>, shouldAnimate: Boolean = true) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SnakePathLayout(shouldAnimate, levels = levels)
    }
}

@Composable
fun SnakePathLayout(
    //adding this for previews oo, so i can see my preview well
    shouldAnimate: Boolean = true,
    levels: List<LevelNode>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val pathStrokeColor = MaterialTheme.greyColors.pathStroke

    val incompleteIconSizePx = with(density) { LearningPathConfig.INCOMPLETE_ICON_SIZE.toPx() }
    val verticalSpacingPx = with(density) { LearningPathConfig.VERTICAL_SPACING.toPx() }

    val animationProgress = remember { Animatable(if (shouldAnimate) 0f else 1f) }


    val badgeScales = remember(levels.size) {
        List(levels.size) { Animatable(if (shouldAnimate) 0f else 1f) }
    }


    LaunchedEffect(levels.size, shouldAnimate) {
        if (shouldAnimate) {
            badgeScales[0].animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )

            // Then animate through each path segment
            for (i in 0 until levels.size - 1) {
                // Draw path segment
                animationProgress.animateTo(
                    targetValue = (i + 1).toFloat(),
                    animationSpec = tween(durationMillis = 200)
                )

                // Small delay before badge pops
                delay(50)

                // Pop in next badge with bubble effect
                badgeScales[i + 1].animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            }
        }
    }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val width = maxWidth
        val widthPx = with(density) { width.toPx() }
        val minPaddingPx = with(density) { LearningPathConfig.MIN_HORIZONTAL_PADDING.toPx() }
        val textContainerWidthPx = with(density) { LearningPathConfig.TEXT_CONTAINER_WIDTH.toPx() }
        val leftOffsetPx = with(density) { 11.dp.toPx() }
        // Calculate how many items can fit per row
        val itemsPerRow = remember(widthPx) {
            calculateItemsPerRow(
                screenWidth = widthPx,
                maxIconSize = incompleteIconSizePx,
                textContainerWidth = textContainerWidthPx,
                minPadding = minPaddingPx
            )
        }

        val centerLine = widthPx / 2f

        // Calculate column positions based on itemsPerRow
        val columnPositions = remember(widthPx, itemsPerRow) {
            calculateColumnPositions(
                screenWidth = widthPx,
                itemsPerRow = itemsPerRow,
                textContainerWidth = textContainerWidthPx,
                minPadding = minPaddingPx
            )
        }

        val rows = (levels.size + itemsPerRow - 1) / itemsPerRow
        val totalHeight = with(density) {
            (rows * (verticalSpacingPx + leftOffsetPx)) + incompleteIconSizePx + 200.dp.toPx()
        }

        // Calculate icon center positions
        val iconCenters = remember(levels, widthPx, itemsPerRow, columnPositions) {
            levels.mapIndexed { index, _ ->
                val row = index / itemsPerRow
                val colInRow = index % itemsPerRow

                // Determine if row is reversed
                val isRowReversed = row % 2 != 0
                val actualCol = if (isRowReversed) {
                    itemsPerRow - 1 - colInRow
                } else {
                    colInRow
                }
                val verticalSpacingWithOffset =
                    if (row > 1) verticalSpacingPx + (leftOffsetPx * row / 2) else verticalSpacingPx
                val offsetDirection = if (row % 2 != 0) -1f else 1f
                val horizontalSpacingWithOffset =
                    if (row > 0) with(density) { 10.dp.toPx() } * offsetDirection else 0f

                val x = columnPositions[actualCol] + horizontalSpacingWithOffset
                val y =
                    if (colInRow == 0) row * (verticalSpacingWithOffset - leftOffsetPx) + (incompleteIconSizePx / 2) else row * verticalSpacingWithOffset + (incompleteIconSizePx / 2)

                Offset(x, y)
            }
        }

        // Layer 1: Canvas for connecting lines with progressive draw animation
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(density) { totalHeight.toDp() })
        ) {
            val currentProgress = animationProgress.value

            for (i in 0 until levels.size - 1) {
                val start = iconCenters[i]
                val end = iconCenters[i + 1]
                val startNode = levels[i]

                val isPathActive = startNode.state == BadgeState.COMPLETED
                val pathEffect = if (isPathActive) null else PathEffect.dashPathEffect(
                    floatArrayOf(12f, 12f),
                    0f
                )

                // Calculate how much of this segment to draw
                val segmentProgress = when {
                    currentProgress >= i + 1 || !shouldAnimate -> 1f
                    currentProgress > i -> currentProgress - i
                    else -> 0f
                }

                if (segmentProgress <= 0f) continue

                val fullPath = Path().apply {
                    val deltaY = end.y - start.y

                    moveTo(start.x, start.y)

                    if (abs(deltaY) < 10f) {
                        lineTo(end.x, end.y)
                    } else {
                        val isStartOnLeft = start.x < centerLine
                        val curveDirection = if (isStartOnLeft) -1f else 1f
                        val swingOffset = 200f * curveDirection

                        val cp1X = start.x + swingOffset
                        val cp1Y = start.y + (deltaY * 0.2f)

                        val cp2X = end.x + swingOffset
                        val cp2Y = end.y - (deltaY * 0.2f)

                        cubicTo(
                            cp1X, cp1Y,
                            cp2X, cp2Y,
                            end.x, end.y
                        )
                    }
                }

                // If segment is partial, extract only the drawn portion
                val pathToDraw = if (segmentProgress < 1f) {
                    val pathMeasure = PathMeasure()
                    pathMeasure.setPath(fullPath, false)
                    val pathLength = pathMeasure.length
                    val stopDistance = pathLength * segmentProgress

                    Path().also { partialPath ->
                        pathMeasure.getSegment(0f, stopDistance, partialPath, true)
                    }
                } else {
                    fullPath
                }

                drawPath(
                    path = pathToDraw,
                    color = pathStrokeColor,
                    style = Stroke(
                        width = LearningPathConfig.STROKE_WIDTH.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                        pathEffect = pathEffect
                    )
                )
            }
        }

        // Layer 2: Badges with scale animation
        iconCenters.forEachIndexed { index, offset ->
            val level = levels[index]
            val iconSize = if (level.state == BadgeState.COMPLETED) {
                LearningPathConfig.COMPLETED_ICON_SIZE
            } else {
                LearningPathConfig.INCOMPLETE_ICON_SIZE
            }

            val containerWidth = 130.dp
            val leftDp = with(density) { offset.x.toDp() } - (containerWidth / 2)
            val topDp = with(density) { offset.y.toDp() } - (iconSize / 2)

            val scale = badgeScales[index].value

            Box(
                modifier = Modifier
                    .offset(x = leftDp, y = topDp)
                    .width(containerWidth)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        alpha = scale
                    }
            ) {
                LevelBadgeItem(level = level, iconSize = iconSize)
            }
        }
    }
}

fun calculateItemsPerRow(
    screenWidth: Float,
    maxIconSize: Float,
    textContainerWidth: Float,
    minPadding: Float
): Int {

    val availableWidth = screenWidth - (2 * minPadding)

    val maxItems = (availableWidth / textContainerWidth).toInt()

    // Ensure at least 2 items per row, but cap at a reasonable maximum (e.g., 4)
    return maxItems.coerceIn(2, 4)
}

/**
 * Calculate X positions for each column - spread items evenly across available width
 */
fun calculateColumnPositions(
    screenWidth: Float,
    itemsPerRow: Int,
    textContainerWidth: Float,
    minPadding: Float
): List<Float> {
    // For 2 items, use a different strategy to spread them out more
    if (itemsPerRow == 2) {
        // Use more of the available width
        val usableWidth = screenWidth - (2 * minPadding)
        val leftX = minPadding + (usableWidth * 0.25f)
        val rightX = minPadding + (usableWidth * 0.75f)
        return listOf(leftX, rightX)
    }

    // For 3+ items, distribute evenly
    val availableWidth = screenWidth - (2 * minPadding)
    val spacing = availableWidth / (itemsPerRow + 1)

    return List(itemsPerRow) { index ->
        minPadding + (spacing * (index + 1))
    }
}

@Composable
fun LevelBadgeItem(
    level: LevelNode,
    iconSize: Dp
) {
    val context = LocalContext.current
    var showAchievement by rememberSaveable {
        mutableStateOf<Badge??>(null)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        val isCompleted = level.state is BadgeState.COMPLETED

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(iconSize)
                .then(
                    if (!isCompleted) Modifier.background(Color.White, CircleShape)
                    else Modifier
                )
        ) {
            if (level.state !is BadgeState.COMPLETED) {
                val progress = if (level.state is BadgeState.InProgress) {
                    level.state.progress
                } else {
                    0f
                }

                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.greyColors.badgeProgress,
                    trackColor = MaterialTheme.greyColors.badgeTrack,
                    strokeWidth = 4.dp,
                )
            }

            Image(
                painter = painterResource(level.icon),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(if (isCompleted) iconSize else iconSize - 14.dp)
                    .clip(CircleShape)
                    .clickable {
                        showAchievement = level.badge
                    }
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = level.title,
            style = MaterialTheme.greyTypography.bodySmall.copy(
                color = MaterialTheme.greyColors.textDefault,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center,
            lineHeight = 15.sp,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth(0.7f)
        )

        level.subTitle?.let { subtitle ->
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.greyTypography.bodySmall.copy(
                    color = MaterialTheme.greyColors.textSoft,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Center,
                lineHeight = 13.sp,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
    }
    if (showAchievement != null) {
        BadgeAchievementBottomSheet(showAchievement!!, onDismiss = {
            showAchievement = null
        }, onShare = {
            showAchievement?.share(context)
        })
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LearningPathPreview() {
    GreyAssesmentTheme {
        val levels = remember {
            listOf(
                LevelNode(
                    1,
                    "Programming Basics",
                    BadgeState.COMPLETED,
                    R.drawable.badge_completed
                ),
                LevelNode(
                    2,
                    "Git & Version Control",
                    BadgeState.COMPLETED,
                    R.drawable.badge_completed
                ),
                LevelNode(
                    3,
                    "Learn React",
                    BadgeState.InProgress(0.5f),
                    R.drawable.badge_in_progress,
                    subTitle = "Component lifecycle"
                ),
                LevelNode(
                    4,
                    "Core Mobile UI Build",
                    BadgeState.LOCKED,
                    R.drawable.badge_not_started
                ),
                LevelNode(
                    5,
                    "Access Device Features",
                    BadgeState.LOCKED,
                    R.drawable.badge_not_started
                ),
                LevelNode(
                    6,
                    "Navigations and Forms",
                    BadgeState.LOCKED,
                    R.drawable.badge_not_started
                ),
                LevelNode(
                    7,
                    "Core Mobile UI Build",
                    BadgeState.LOCKED,
                    R.drawable.badge_not_started
                ),
                LevelNode(
                    8,
                    "Access Device Features",
                    BadgeState.LOCKED,
                    R.drawable.badge_not_started
                ),
                LevelNode(
                    9,
                    "Navigations and Forms",
                    BadgeState.LOCKED,
                    R.drawable.badge_not_started
                ),
                LevelNode(10, "State Management", BadgeState.LOCKED, R.drawable.badge_not_started),
            )
        }
        // this preview hides the animation
        PathsCustomComponentBySimon(levels, false)
    }
}