package com.simon.greyassesment.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.simon.greyassesment.R
import com.simon.greyassesment.features.home.presentation.screens.LearningHomeScreen
import com.simon.greyassesment.features.learnpath.screens.BadgeState
import com.simon.greyassesment.features.learnpath.screens.LearningPathScreen
import com.simon.greyassesment.features.learnpath.screens.LevelNode

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = NavRoutes.Home.route) {
            LearningHomeScreen(
                onNavigateToLearningPath = {
                    navController.navigate(NavRoutes.LearningPath.route)
                }
            )
        }

        composable(route = NavRoutes.LearningPath.route) {
            val levels = remember {
                listOf(
                    LevelNode(1, "Programming Basics", BadgeState.COMPLETED, R.drawable.badge_completed),
                    LevelNode(2, "Git & Version Control", BadgeState.COMPLETED, R.drawable.badge_completed),
                    LevelNode(3, "Learn React", BadgeState.InProgress(0.5f), R.drawable.badge_in_progress),
                    LevelNode(4, "Core Mobile UI Build", BadgeState.LOCKED, R.drawable.badge_not_started),
                    LevelNode(5, "Access Device Features", BadgeState.LOCKED, R.drawable.badge_not_started),
                    LevelNode(6, "Navigations and Forms", BadgeState.LOCKED, R.drawable.badge_not_started),
                    LevelNode(7, "State Management", BadgeState.LOCKED, R.drawable.badge_not_started),
                )
            }
            LearningPathScreen(levels)
        }

        composable(
            route = NavRoutes.TaskDetail.route,
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: return@composable
            // TaskDetailScreen(taskId = taskId) - implement when ready
        }
    }
}