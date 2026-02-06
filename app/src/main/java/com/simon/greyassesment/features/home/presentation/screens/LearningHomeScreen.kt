package com.simon.greyassesment.features.home.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.simon.greyassesment.data.datasource.MockLearningDataSource
import com.simon.greyassesment.domain.model.ActiveLearningPathSummary
import com.simon.greyassesment.domain.model.DailyTask
import com.simon.greyassesment.features.home.presentation.components.HomeHeaderScreen
import com.simon.greyassesment.features.home.presentation.components.HomeScreenTaskDetailsSection
import com.simon.greyassesment.features.home.presentation.components.TaskCardContent
import com.simon.greyassesment.features.home.presentation.viewmodel.HomeUiState
import com.simon.greyassesment.features.home.presentation.viewmodel.HomeViewModel
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LearningHomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToLearningPath: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    LearningHomeScreenContent(
        state = state,
        onNavigateToLearningPath = onNavigateToLearningPath
    )
}

@Composable
private fun LearningHomeScreenContent(
    state: HomeUiState,
    onNavigateToLearningPath: () -> Unit = {}
) {
    var overLapHeight by remember { mutableStateOf(60.dp) }

    val headerAlpha = remember { Animatable(0f) }
    val headerOffset = remember { Animatable(-100f) }
    val cardAlpha = remember { Animatable(0f) }
    val cardOffset = remember { Animatable(400f) }
    val detailsAlpha = remember { Animatable(0f) }
    val detailsOffset = remember { Animatable(300f) }

    LaunchedEffect(state.user) {
        if (state.user != null) {
            launch {
                headerAlpha.animateTo(1f, tween(600))
            }
            launch {
                headerOffset.animateTo(0f, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow))
            }

            delay(200)

            launch {
                cardAlpha.animateTo(1f, tween(500))
            }
            launch {
                cardOffset.animateTo(0f, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow))
            }

            delay(350)

            launch {
                detailsAlpha.animateTo(1f, tween(500))
            }
            launch {
                detailsOffset.animateTo(0f, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow))
            }
        }
    }

    Surface {
        Column(
            Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            if (state.user != null) {
                OverlappingHeaderLayout(
                    modifier = Modifier
                        .alpha(headerAlpha.value)
                        .offset { IntOffset(0, headerOffset.value.toInt()) },
                    header = {
                        HomeHeaderScreen(
                            user = state.user,
                            greeting = state.greeting,
                            overlapHeight = overLapHeight
                        )
                    },
                    overlappingContent = {
                        TaskCardContent(
                            task = state.dailyTask,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .alpha(cardAlpha.value)
                                .offset { IntOffset(0, cardOffset.value.toInt()) }
                        )
                    }
                ) {
                    overLapHeight = it
                }
                Spacer(Modifier.height(32.dp))
                HomeScreenTaskDetailsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .alpha(detailsAlpha.value)
                        .offset { IntOffset(0, detailsOffset.value.toInt()) },
                    activeLearningPath = state.activeLearningPath
                )
            }
        }
    }
}

@Composable
private fun OverlappingHeaderLayout(
    header: @Composable () -> Unit,
    overlappingContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onOverlappingHeight: (Dp) -> Unit = {},
) {
    Layout(
        contents = listOf(header, overlappingContent),
        modifier = modifier
    ) { (headerMeasurables, cardMeasurables), constraints ->
        val headerPlaceable = headerMeasurables.first().measure(constraints)
        val cardPlaceable = cardMeasurables.first().measure(constraints)

        val overlapPx = cardPlaceable.height / 2

        val totalHeight = headerPlaceable.height + overlapPx

        onOverlappingHeight(overlapPx.toDp())

        layout(constraints.maxWidth, totalHeight) {
            headerPlaceable.place(0, 0)
            cardPlaceable.place(0, headerPlaceable.height - overlapPx)
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LearningHomeScreenPreview() {
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

    val activeLearningPath = ActiveLearningPathSummary(
        course = activeCourse,
        currentPath = currentPath,
        currentTopic = currentTopic
    )

    val dummyState = HomeUiState(
        isLoading = false,
        user = MockLearningDataSource.currentUser,
        greeting = "Good morning ${MockLearningDataSource.currentUser.displayName}!",
        dailyTask = dailyTask,
        activeLearningPath = activeLearningPath,
        earnedBadges = MockLearningDataSource.earnedBadges
    )

    GreyAssesmentTheme {
        LearningHomeScreenContent(state = dummyState)
    }
}