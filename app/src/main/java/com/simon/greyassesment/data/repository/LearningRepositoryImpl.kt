package com.simon.greyassesment.data.repository

import com.simon.greyassesment.data.datasource.MockLearningDataSource
import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.domain.model.Course
import com.simon.greyassesment.domain.model.DailyTask
import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.model.User
import com.simon.greyassesment.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LearningRepositoryImpl : LearningRepository {

    private val coursesState = MutableStateFlow(MockLearningDataSource.allCourses)
    private val activeCourseId = MutableStateFlow(MockLearningDataSource.fullstackMobileCourse.id)

    override fun getUser(): Flow<User> = flow {
        emit(MockLearningDataSource.currentUser)
    }

    override fun getCourses(): Flow<List<Course>> = coursesState

    override fun getCourseById(courseId: String): Flow<Course?> = coursesState.map { courses ->
        courses.find { it.id == courseId }
    }

    override fun getActiveCourse(): Flow<Course?> = coursesState.map { courses ->
        courses.find { it.id == activeCourseId.value }
    }

    override fun getPathsForCourse(courseId: String): Flow<List<Path>> = coursesState.map { courses ->
        courses.find { it.id == courseId }?.paths ?: emptyList()
    }

    override fun getPathById(pathId: String): Flow<Path?> = coursesState.map { courses ->
        courses.flatMap { it.paths }.find { it.id == pathId }
    }

    override fun getEarnedBadges(): Flow<List<Badge>> = coursesState.map { courses ->
        courses.flatMap { it.earnedBadges }
    }

    override fun getAllBadges(): Flow<List<Badge>> = coursesState.map { courses ->
        courses.flatMap { course -> course.paths.map { it.badge } }
    }

    override fun getDailyTask(): Flow<DailyTask?> = coursesState.map { courses ->
        val activeCourse = courses.find { it.id == activeCourseId.value } ?: return@map null
        val currentPath = activeCourse.currentPath ?: return@map null
        val currentTopic = currentPath.currentTopic ?: return@map null
        val currentTask = currentTopic.currentTask ?: return@map null

        DailyTask(
            task = currentTask,
            topic = currentTopic,
            path = currentPath,
            course = activeCourse
        )
    }

    override suspend fun completeTask(taskId: String) {
        val updatedCourses = coursesState.value.map { course ->
            course.copy(
                paths = course.paths.map { path ->
                    path.copy(
                        topics = path.topics.map { topic ->
                            topic.copy(
                                tasks = topic.tasks.map { task ->
                                    if (task.id == taskId) task.copy(isCompleted = true) else task
                                }
                            )
                        }
                    )
                }
            )
        }
        coursesState.value = updatedCourses
    }

    override suspend fun setActiveCourse(courseId: String) {
        activeCourseId.value = courseId
    }
}