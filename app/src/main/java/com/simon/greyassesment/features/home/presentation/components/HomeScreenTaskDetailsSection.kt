package com.simon.greyassesment.features.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simon.greyassesment.R
import com.simon.greyassesment.data.datasource.MockLearningDataSource
import com.simon.greyassesment.domain.model.ActiveLearningPathSummary
import com.simon.greyassesment.ui.components.GreyButton
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import com.simon.greyassesment.ui.theme.greyColors
import com.simon.greyassesment.ui.theme.greyShapes
import com.simon.greyassesment.ui.theme.greyTypography

@Composable
fun HomeScreenTaskDetailsSection(
    modifier: Modifier = Modifier,
    activeLearningPath: ActiveLearningPathSummary?
) {

    if (activeLearningPath != null) {
        Column(modifier) {
            Text(
                text = stringResource(R.string.active_learning_path),
                style = MaterialTheme.greyTypography.titleLarge.copy(
                    fontSize = 16.sp
                )
            )
            Spacer(Modifier.height(12.dp))
            Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {

                Column(Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
                    Text(
                        text = activeLearningPath.course.title,
                        style = MaterialTheme.greyTypography.bodyMedium.copy(
                            fontSize = 14.sp,
                            color = MaterialTheme.greyColors.textDefault,
                            fontWeight = FontWeight.W500
                        )
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Stage ${activeLearningPath.course.currentStage} of ${activeLearningPath.course.totalPaths}",
                            style = MaterialTheme.greyTypography.bodyMedium.copy(
                                fontSize = 12.sp,
                                color = MaterialTheme.greyColors.textSoft,
                                fontWeight = FontWeight.W500
                            )
                        )

                        Spacer(Modifier.width(4.dp))
                        LinearProgressIndicator(
                            progress = {
                                activeLearningPath.course.progress
                            },
                            gapSize = (-7).dp,
                            drawStopIndicator = {},
                            color = MaterialTheme.greyColors.purple500,
                            trackColor = MaterialTheme.greyColors.purple100,
                            strokeCap = StrokeCap.Round,
                            modifier = Modifier
                                .height(6.dp)
                                .fillMaxWidth(0.7f)
                        )
                    }
                }
                Spacer(Modifier.height(2.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp, MaterialTheme.greyColors.borderVariant,
                            MaterialTheme.greyShapes.cardMedium
                        )
                        .padding(horizontal = 12.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.badge_in_progress),
                        contentDescription = "Badge",
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            activeLearningPath.currentPath.title,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.greyColors.textDefault
                            )
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            activeLearningPath.currentPath.currentTopic?.title.orEmpty(),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.greyColors.textSoft
                            )
                        )

                    }

                }
                Spacer(Modifier.height(16.dp))
                GreyButton(
                    text = stringResource(R.string.view_full_path),
                    icon = painterResource(R.drawable.icon_arrow_right),
                    onClick = {}
                )
            }
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.badges),
                style = MaterialTheme.greyTypography.titleLarge.copy(
                    fontSize = 16.sp
                )
            )
            activeLearningPath.course.earnedBadges.chunked(3).forEach { badgeRow ->
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    badgeRow.map { badge ->
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Image(
                                painterResource(badge.icon),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .aspectRatio(1f),
                                contentDescription = badge.name
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = badge.name,
                                style = MaterialTheme.greyTypography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W600,
                                    color = MaterialTheme.greyColors.textDefault
                                ),
                                textAlign = TextAlign.Center,
                                maxLines = 2
                            )
                            Spacer(Modifier.height(3.dp))
                            Text(
                                text = badge.description,
                                style = MaterialTheme.greyTypography.bodySmall.copy(
                                    fontSize = 10.sp
                                ),
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                    repeat(3 - badgeRow.size) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenActivePathPreview() {
    val courses = MockLearningDataSource.allCourses
    val activeCourseId = MockLearningDataSource.fullstackMobileCourse.id

    val course = courses.find { it.id == activeCourseId } ?: return
    val currentPath = course.currentPath ?: return
    val currentTopic = currentPath.currentTopic ?: return
    val summary = ActiveLearningPathSummary(
        course = course,
        currentPath = currentPath,
        currentTopic = currentTopic
    )
    GreyAssesmentTheme {
        HomeScreenTaskDetailsSection(
            Modifier
                .statusBarsPadding()
                .padding(16.dp), summary
        )
    }
}