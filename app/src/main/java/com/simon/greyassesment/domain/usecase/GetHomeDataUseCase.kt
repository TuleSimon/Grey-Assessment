package com.simon.greyassesment.domain.usecase

import com.simon.greyassesment.domain.model.ActiveLearningPathSummary
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.domain.model.DailyTask
import com.simon.greyassesment.domain.model.User
import com.simon.greyassesment.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class HomeData(
    val user: User,
    val dailyTask: DailyTask?,
    val activeLearningPath: ActiveLearningPathSummary?,
    val earnedBadges: List<Badge>
)

class GetHomeDataUseCase @Inject constructor(
    private val repository: LearningRepository
) {
    operator fun invoke(): Flow<HomeData> = combine(
        repository.getUser(),
        repository.getDailyTask(),
        repository.getActiveCourse(),
        repository.getEarnedBadges()
    ) { user, dailyTask, activeCourse, earnedBadges ->

        val activeLearningPath = activeCourse?.let { course ->
            val currentPath = course.currentPath
            val currentTopic = currentPath?.currentTopic
            if (currentPath != null && currentTopic != null) {
                ActiveLearningPathSummary(
                    course = course,
                    currentPath = currentPath,
                    currentTopic = currentTopic
                )
            } else null
        }

        HomeData(
            user = user,
            dailyTask = dailyTask,
            activeLearningPath = activeLearningPath,
            earnedBadges = earnedBadges
        )
    }
}