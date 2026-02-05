package com.simon.greyassesment.domain.model

import androidx.annotation.DrawableRes

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val streakDays: Int = 0
) {
    val initials: String get() = "${firstName.firstOrNull() ?: ""}${lastName.firstOrNull() ?: ""}".uppercase()
    val displayName: String get() = firstName
}

data class Badge(
    val id: String,
    val name: String,
    val description: String,
    @DrawableRes val icon: Int,
    val earnedAt: Long? = null
) {
    val isEarned: Boolean get() = earnedAt != null
}


data class Task(
    val id: String,
    val title: String,
    val isCompleted: Boolean = false,
    val orderIndex: Int
)


data class Topic(
    val id: String,
    val title: String,
    val tasks: List<Task>,
    val orderIndex: Int
) {
    val totalTasks: Int get() = tasks.size
    val completedTasks: Int get() = tasks.count { it.isCompleted }
    val isCompleted: Boolean get() = totalTasks > 0 && completedTasks == totalTasks
    val progress: Float get() = if (totalTasks == 0) 0f else completedTasks.toFloat() / totalTasks
    val currentTask: Task? get() = tasks.firstOrNull { !it.isCompleted }
}


enum class PathStatus {
    LOCKED,
    IN_PROGRESS,
    COMPLETED
}


data class Path(
    val id: String,
    val title: String,
    val description: String,
    @DrawableRes val icon: Int,
    val topics: List<Topic>,
    val badge: Badge,
    val orderIndex: Int,
    val isUnlocked: Boolean = false
) {
    val totalTopics: Int get() = topics.size
    val completedTopics: Int get() = topics.count { it.isCompleted }
    val progress: Float get() = if (topics.isEmpty()) 0f else {
        val totalTasks = topics.sumOf { it.totalTasks }
        val completedTasks = topics.sumOf { it.completedTasks }
        if (totalTasks == 0) 0f else completedTasks.toFloat() / totalTasks
    }
    val status: PathStatus
        get() = when {
            completedTopics == totalTopics && totalTopics > 0 -> PathStatus.COMPLETED
            isUnlocked && topics.any { it.completedTasks > 0 } -> PathStatus.IN_PROGRESS
            isUnlocked -> PathStatus.IN_PROGRESS
            else -> PathStatus.LOCKED
        }
    val currentTopic: Topic? get() = topics.firstOrNull { !it.isCompleted }
}


data class Course(
    val id: String,
    val title: String,
    val description: String,
    @DrawableRes val icon: Int,
    val paths: List<Path>
) {
    val totalPaths: Int get() = paths.size
    val completedPaths: Int get() = paths.count { it.status == PathStatus.COMPLETED }
    val currentStage: Int get() = completedPaths + 1
    val progress: Float get() = if (totalPaths == 0) 0f else completedPaths.toFloat() / totalPaths
    val currentPath: Path? get() = paths.firstOrNull { it.status != PathStatus.COMPLETED }
    val earnedBadges: List<Badge> get() = paths
        .filter { it.status == PathStatus.COMPLETED }
        .map { it.badge }
}


data class DailyTask(
    val task: Task,
    val topic: Topic,
    val path: Path,
    val course: Course
)


data class ActiveLearningPathSummary(
    val course: Course,
    val currentPath: Path,
    val currentTopic: Topic
)