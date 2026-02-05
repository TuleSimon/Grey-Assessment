package com.simon.greyassesment.domain.usecase

import com.simon.greyassesment.domain.model.Course
import com.simon.greyassesment.domain.model.Path
import com.simon.greyassesment.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLearningPathUseCase @Inject constructor(
    private val repository: LearningRepository
) {
    fun getActiveCourse(): Flow<Course?> = repository.getActiveCourse()

    fun getPathsForCourse(courseId: String): Flow<List<Path>> =
        repository.getPathsForCourse(courseId)

    fun getCourseById(courseId: String): Flow<Course?> =
        repository.getCourseById(courseId)
}