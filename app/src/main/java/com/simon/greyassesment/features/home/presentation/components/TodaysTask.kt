package com.simon.greyassesment.features.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simon.greyassesment.R
import com.simon.greyassesment.data.datasource.MockLearningDataSource
import com.simon.greyassesment.domain.model.DailyTask
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import com.simon.greyassesment.ui.theme.greyColors
import com.simon.greyassesment.ui.theme.greyShapes


@Composable
fun TaskCardContent(task: DailyTask?, modifier: Modifier) {
    if(task==null) return
    Card(
        modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.greyColors.surface
        ),
        shape = MaterialTheme.greyShapes.card
    ) {

        Column(Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text(
                stringResource(R.string.for_today),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.greyColors.textDefault
                )
            )
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(Modifier.size(48.dp)) {
                    CircularProgressIndicator(
                        trackColor = MaterialTheme.greyColors.border,
                        progress = { 0f },
                        modifier = Modifier.fillMaxSize(),
                        strokeWidth = 3.dp
                    )
                    Image(
                        painter = painterResource(R.drawable.badge_not_started),
                        contentDescription = "Badge",
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column() {
                    Text(
                        task.task.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.greyColors.textDefault
                        )
                    )
                    Spacer(Modifier.height(3.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.icon_note),
                            contentDescription = "Badge",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            task.topic.title,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.greyColors.textSoft
                            )
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.right_curved),
                    contentDescription = "Badge",
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TaskCardContentPreview() {
    val courses = MockLearningDataSource.allCourses
    val activeCourseId = MockLearningDataSource.fullstackMobileCourse.id

    val activeCourse = courses.find { it.id == activeCourseId } ?: return
    val currentPath = activeCourse.currentPath ?: return
    val currentTopic = currentPath.currentTopic ?: return
    val currentTask = currentTopic.currentTask ?: return

    val dailyTask = DailyTask(
        task = currentTask,
        topic = currentTopic,
        path = currentPath,
        course = activeCourse
    )

    GreyAssesmentTheme {
        TaskCardContent(
            dailyTask,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
