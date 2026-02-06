package com.simon.greyassesment.features.home.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simon.greyassesment.R
import com.simon.greyassesment.data.datasource.MockLearningDataSource
import com.simon.greyassesment.domain.model.User
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import com.simon.greyassesment.ui.theme.greyColors
import com.simon.greyassesment.ui.theme.greyShapes
import com.simon.greyassesment.ui.theme.greySpacing
import com.simon.greyassesment.ui.theme.greyTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeHeaderScreen(user: User, greeting: String, overlapHeight: Dp) {
    val topRowAlpha = remember { Animatable(0f) }
    val mascotScale = remember { Animatable(0f) }
    val mascotAlpha = remember { Animatable(0f) }
    val greetingOffset = remember { Animatable(300f) }
    val greetingAlpha = remember { Animatable(0f) }
    val subtitleOffset = remember { Animatable(-300f) }
    val subtitleAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            topRowAlpha.animateTo(1f, tween(400))
        }

        delay(100)

        launch {
            mascotAlpha.animateTo(1f, tween(400))
        }
        launch {
            mascotScale.animateTo(1f, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow))
        }

        delay(200)

        launch {
            greetingAlpha.animateTo(1f, tween(400))
        }
        launch {
            greetingOffset.animateTo(0f, spring(Spring.DampingRatioLowBouncy, Spring.StiffnessMediumLow))
        }

        delay(100)

        launch {
            subtitleAlpha.animateTo(1f, tween(400))
        }
        launch {
            subtitleOffset.animateTo(0f, spring(Spring.DampingRatioLowBouncy, Spring.StiffnessMediumLow))
        }
    }

    Box(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Image(
            painter = painterResource(R.drawable.home_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentDescription = "Home Bg"
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.greySpacing.spacing16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .alpha(topRowAlpha.value),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledActionButton {
                    Text(
                        user.initials,
                        style = MaterialTheme.greyTypography.bodyLarge.copy(
                            color = MaterialTheme.greyColors.homeIconTint,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ), maxLines = 1
                    )
                }
                if (user.streakDays > 0) {
                    Row(
                        Modifier
                            .border(
                                1.dp, MaterialTheme.greyColors.borderAccent,
                                MaterialTheme.greyShapes.full
                            )
                            .background(
                                MaterialTheme.greyColors.purple100.copy(alpha = 0.1f),
                                MaterialTheme.greyShapes.full
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_streak),
                            contentDescription = "treak",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .width(12.dp)
                                .height(14.dp)
                        )
                        Text(
                            "${user.streakDays} Days",
                            style = MaterialTheme.greyTypography.bodyMedium.copy(
                                color = MaterialTheme.greyColors.purple600,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                FilledActionButton {
                    Image(
                        painter = painterResource(R.drawable.icon_chat),
                        contentDescription = "Chat",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(Modifier.height(MaterialTheme.greySpacing.spacing25))
            Image(
                painter = painterResource(R.drawable.home_mascot),
                contentDescription = "Mascot",
                modifier = Modifier
                    .width(200.dp)
                    .height(210.dp)
                    .scale(mascotScale.value)
                    .alpha(mascotAlpha.value)
            )
            Spacer(Modifier.height(MaterialTheme.greySpacing.spacing7))
            Text(
                greeting,
                style = MaterialTheme.greyTypography.titleLarge.copy(fontSize = 28.sp),
                modifier = Modifier
                    .alpha(greetingAlpha.value)
                    .offset { IntOffset(greetingOffset.value.toInt(), 0) }
            )
            Spacer(Modifier.height(MaterialTheme.greySpacing.spacing4))
            Text(
                stringResource(R.string.you_re_closer_than_you_think),
                style = MaterialTheme.greyTypography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .alpha(subtitleAlpha.value)
                    .offset { IntOffset(subtitleOffset.value.toInt(), 0) }
            )
            Spacer(Modifier.height(MaterialTheme.greySpacing.spacing25))
            Spacer(Modifier.height(overlapHeight))
        }
    }
}

@Composable
fun FilledActionButton(content: @Composable () -> Unit = {}) {
    Box(
        Modifier
            .background(MaterialTheme.greyColors.homeIconContainer, CircleShape)
            .clip(CircleShape)
            .padding(12.dp)
    ) {
        content()
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    GreyAssesmentTheme {
        HomeHeaderScreen(MockLearningDataSource.currentUser, "Good morning Alex", overlapHeight = 30.dp)
    }
}