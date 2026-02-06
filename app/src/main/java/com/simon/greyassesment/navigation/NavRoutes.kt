package com.simon.greyassesment.navigation

sealed class NavRoutes(val route: String) {
    data object Home : NavRoutes("home")
    data object LearningPath : NavRoutes("learning_path")
    data object TaskDetail : NavRoutes("task_detail/{taskId}") {
        fun createRoute(taskId: String) = "task_detail/$taskId"
    }
}