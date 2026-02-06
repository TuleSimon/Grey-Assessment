package com.simon.greyassesment.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.simon.greyassesment.features.home.presentation.screens.LearningHomeScreen
import com.simon.greyassesment.features.home.presentation.screens.LearningHomeScreenRoute
import com.simon.greyassesment.features.learnpath.presentation.screens.LearningPathHomeScreen
import com.simon.greyassesment.features.learnpath.presentation.screens.LearningPathHomeScreenRoute

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any = LearningHomeScreenRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<LearningHomeScreenRoute>(
            enterTransition = { fadeIn() + slideInVertically { it / 2 } },
            exitTransition = { fadeOut() + slideOutVertically { -it } },
            popExitTransition = { fadeOut() + slideOutVertically { -it } },
            popEnterTransition = { fadeIn() + slideInVertically { it / 2 } }
        ) {
            LearningHomeScreen(
                onNavigateToLearningPath = {
                    navController.navigate(LearningPathHomeScreenRoute(it))
                }
            )
        }

        composable<LearningPathHomeScreenRoute>(
            enterTransition = { fadeIn() + slideInVertically { -it } },
            exitTransition = { fadeOut() + slideOutVertically { -it } },
            popExitTransition = { fadeOut() + slideOutVertically { -it } },
            popEnterTransition = { fadeIn() + slideInVertically { -it } }
        ) {
            val courseId = it.toRoute<LearningPathHomeScreenRoute>().courseId
            LearningPathHomeScreen(courseId) {
                navController.navigateUp()
            }
        }


    }
}