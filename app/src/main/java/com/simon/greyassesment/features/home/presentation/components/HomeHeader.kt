package com.simon.greyassesment.features.home.presentation.components

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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

@Composable
fun HomeHeaderScreen(user: User, greeting: String, overlapHeight: Dp) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Image(
            painter = painterResource(R.drawable.home_background),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentDescription = "Home Bg"
        )
        Column(
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(MaterialTheme.greySpacing.spacing16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                            .padding(8.dp),
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
                    .width(158.dp)
                    .height(193.dp)
            )
            Spacer(Modifier.height(MaterialTheme.greySpacing.spacing7))
            Text(
                greeting, style = MaterialTheme.greyTypography.titleLarge.copy(
                    fontSize = 28.sp
                )
            )
            Spacer(Modifier.height(MaterialTheme.greySpacing.spacing4))
            Text(
                stringResource(R.string.you_re_closer_than_you_think),
                style = MaterialTheme.greyTypography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
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