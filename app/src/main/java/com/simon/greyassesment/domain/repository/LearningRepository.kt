package com.simon.greyassesment.domain.repository

import com.simon.greyassesment.domain.model.Badge
import com.simon.greyassesment.domain.model.Course
import com.simon.greyassesment.domain.model.DailyTask
import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LearningRepository {

    fun getUser(): Flow<User>

    fun getCourses(): Flow<List<Course>>
    fun getCourseById(courseId: String): Flow<Course?>
    fun getActiveCourse(): Flow<Course?>

    fun getPathsForCourse(courseId: String): Flow<List<Path>>
    fun getPathById(pathId: String): Flow<Path?>

    fun getEarnedBadges(): Flow<List<Badge>>
    fun getAllBadges(): Flow<List<Badge>>

    fun getDailyTask(): Flow<DailyTask?>

    suspend fun completeTask(taskId: String)
    suspend fun setActiveCourse(courseId: String)
}