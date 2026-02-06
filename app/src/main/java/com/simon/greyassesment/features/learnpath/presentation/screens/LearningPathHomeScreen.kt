package com.simon.greyassesment.features.learnpath.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simon.greyassesment.R
import com.simon.greyassesment.data.datasource.MockLearningDataSource
import com.simon.greyassesment.features.learnpath.domain.LearningPathMapper
import com.simon.greyassesment.features.learnpath.presentation.components.PathsCustomComponentBySimon
import com.simon.greyassesment.features.learnpath.viewModel.LearningPathUiState
import com.simon.greyassesment.features.learnpath.viewModel.LearningPathViewModel
import com.simon.greyassesment.ui.theme.GreyAssesmentTheme
import com.simon.greyassesment.ui.theme.greyColors
import com.simon.greyassesment.ui.theme.greySpacing
import com.simon.greyassesment.ui.theme.greyTypography
import kotlinx.serialization.Serializable

@Serializable
data class LearningPathHomeScreenRoute(
    val courseId: String
)

@Composable
fun LearningPathHomeScreen(
    courseId: String,
    learningPathViewModel: LearningPathViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        learningPathViewModel.loadCourse(courseId)
    }
    val state = learningPathViewModel.uiState.collectAsStateWithLifecycle()
    LearningPathHomeScreenContent(state.value)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LearningPathHomeScreenContent(state: LearningPathUiState) {
    val activeCourse = state.course
    val activePath = activeCourse?.currentPath

    // i could move this to viewModel, if i forget i didn't have time
    val levelNodes = remember(state.paths) {
        LearningPathMapper.mapPathsToLevelNodes(state.paths)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = MaterialTheme.greySpacing.spacing16),
                title = {
                    Text("")
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.top_arrow_back),
                        tint = MaterialTheme.greyColors.iconDefault,
                        contentDescription = "Back Arrow",
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        }
    ) {
        if (activeCourse == null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.greySpacing.spacing16)
            ) {
                Text(
                    stringResource(R.string.no_courses_found),
                    style = MaterialTheme.greyTypography.titleLarge
                )
            }
        } else {
            Column(
                Modifier
                    .padding(it)
                    .padding(horizontal = MaterialTheme.greySpacing.spacing16)
            ) {
                if (activePath != null) {
                    Text(
                        "Stage ${activeCourse.completedPaths} of ${activeCourse.paths.size}",
                        style = MaterialTheme.greyTypography.bodySmall,
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    activeCourse.title,
                    style = MaterialTheme.greyTypography.titleLarge.copy(
                        fontSize = 24.sp
                    ),
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
                Spacer(Modifier.height(10.dp))
                PathsCustomComponentBySimon(levelNodes)
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun PathScreenPreview() {
    val courses = MockLearningDataSource.allCourses
    val activeCourseId = MockLearningDataSource.fullstackMobileCourse.id

    val activeCourse = courses.find { it.id == activeCourseId } ?: return

    GreyAssesmentTheme {
        LearningPathHomeScreenContent(
            LearningPathUiState(
                isLoading = false,
                course = activeCourse,
                paths = activeCourse.paths
            )
        )
    }
}